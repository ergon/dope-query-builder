package ch.ergon.dope.mongo

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.merge
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectLimitClause
import ch.ergon.dope.resolvable.clause.model.SelectOffsetClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.WhereClause
import ch.ergon.dope.resolvable.clause.model.mergeable.MergeableClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.type.relational.EqualsExpression

internal object ClauseResolver {
    fun resolve(manager: DopeQueryManager<MongoDopeQuery>, clause: Clause) =
        when (clause) {
            is SelectClause -> {
                val firstDopeQuery = clause.expression
                val restDopeQueries = clause.expressions
                val all =
                    listOf(firstDopeQuery, *restDopeQueries.toTypedArray()).map { Pair(it.toDopeQuery(manager), it) }

                MongoDopeQuery(
                    queryString = "{ \$project: {" + all.joinToString(", ") {
                        when (it.second) {
                            is AliasedTypeExpression<*> -> {
                                it.first.queryString
                            }

                            else -> {
                                "${it.first.queryString}: 1"
                            }
                        }
                    } + ", \"_id\": 0 } }",
                    namedParams = all.map { it.first.namedParams }.merge(),
                )
            }

            is FromClause<*> -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(manager)
                val bucket = if (clause.fromable is Bucket) {
                    clause.fromable as Bucket
                } else {
                    null
                }
                MongoDopeQuery(
                    queryString = parentDopeQuery.queryString,
                    bucket = bucket,
                )
            }

            is MergeableClause<*> -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(manager)
                val mergeable = clause.mergeable as Bucket
                val condition = clause.condition ?: error("JOIN ON requires a condition for Mongo lookup")

                val rendered = renderForLookupExpr(manager, condition)

                val letJson = if (rendered.neededLets.isEmpty()) {
                    "" // no let section needed
                } else {
                    rendered.neededLets.joinToString(
                        prefix = "\"let\": { ",
                        postfix = " },",
                        separator = ", ",
                    ) { name -> "\"${name.second}\": \"\$${name.first}\"" }
                }

                val lookup =
                    "\$lookup : {" +
                        "\"from\" : \"${mergeable.name}\"," +
                        (if (letJson.isNotEmpty()) " $letJson" else "") +
                        "\"pipeline\" : [ { \"\$match\" : { \"\$expr\": ${rendered.expr} } } ]," +
                        "\"as\" : \"${clause.bucket?.name ?: "test"}\"" +
                        "}"

                MongoDopeQuery(
                    queryString = "${parentDopeQuery.queryString},\n{ $lookup }",
                    bucket = parentDopeQuery.bucket,
                )
            }

            is WhereClause -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(manager)
                val whereDopeQuery = clause.whereExpression.toDopeQuery(manager)
                MongoDopeQuery(
                    queryString = "{ \$match: ${whereDopeQuery.queryString} },\n${parentDopeQuery.queryString}",
//                    queryString = "${parentDopeQuery.queryString},\n{ \$match: ${whereDopeQuery.queryString} }",
                    namedParams = parentDopeQuery.namedParams.merge(whereDopeQuery.namedParams),
                )
            }

            is SelectOrderByClause<*> -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(manager)
                val orderExpressionDopeQuery = clause.orderExpression.toDopeQuery(manager)
                val additionalOrderExpressionDopeQuery = clause.additionalOrderExpressions.map { it.toDopeQuery(manager) }
                val orderExpressions = listOf(orderExpressionDopeQuery) + additionalOrderExpressionDopeQuery
                MongoDopeQuery(
                    queryString = "${parentDopeQuery.queryString},\n{ \$sort: { ${orderExpressions.joinToString(", ") { it.queryString }} } }",
                    namedParams = parentDopeQuery.namedParams.merge(*orderExpressions.map { it.namedParams }.toTypedArray()),
                )
            }

            is SelectOffsetClause<*> -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(manager)
                val offsetDopeQuery = clause.numberExpression.toDopeQuery(manager)
                MongoDopeQuery(
                    queryString = "${parentDopeQuery.queryString},\n{ \$skip: ${offsetDopeQuery.queryString} }",
                    namedParams = parentDopeQuery.namedParams.merge(offsetDopeQuery.namedParams),
                )
            }

            is SelectLimitClause<*> -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(manager)
                val limitDopeQuery = clause.numberExpression.toDopeQuery(manager)
                MongoDopeQuery(
                    queryString = "${parentDopeQuery.queryString},\n{ \$limit: ${limitDopeQuery.queryString} }",
                    namedParams = parentDopeQuery.namedParams.merge(limitDopeQuery.namedParams),
                )
            }

            else -> TODO("not yet implemented: $clause")
        }

    private data class ExprRender(
        val expr: String, // JSON for $expr
        val neededLets: Set<Pair<String, String>>, // names of left-side fields to expose in let
    )

    private fun renderForLookupExpr(
        manager: DopeQueryManager<MongoDopeQuery>,
        condition: Expression<*>,
    ): ExprRender {
        fun go(e: Expression<*>): Pair<String, Set<Pair<String, String>>> = when (e) {
            is EqualsExpression<*> -> {
                val (l, ll) = go(e.left)
                val (r, rl) = go(e.right)
                "{ \"\$eq\": [ $l, $r ] }" to (ll + rl)
            }

            // If you have other boolean/arith ops, add cases here, e.g. And/Or/Gt/Lt/etc.

            is AliasedTypeExpression<*> -> {
                // Assuming AliasedTypeExpression can wrap a field; delegate down if you have a .expression
                go(e.typeExpression)
            }

            is ch.ergon.dope.resolvable.expression.type.Field<*> -> {
                // You may have a different class for fields; adapt this match accordingly.
                val iteratorName = manager.iteratorManager.getIteratorName()
                val fieldName = e.name
                val isLeft = !fieldName.contains("2") // (e.path == leftBucket.name) // adjust depending on your model
                val ref = if (isLeft) "\"\$\$${iteratorName}\"" else "\"\$${fieldName}\""
                ref to (
                    if (isLeft) {
                        setOf(fieldName to iteratorName)
                    } else {
                        emptySet()
                    }
                    )
            }

            else -> {
                // Fallback to your existing toDopeQuery and assume it returns a JSON fragment.
                val dq = e.toDopeQuery(manager)
                dq.queryString to emptySet()
            }
        }

        val (expr, lets) = go(condition)
        return ExprRender(expr = expr, neededLets = lets)
    }
}
