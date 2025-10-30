package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.merge
import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.model.AliasedUnnestClause
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.GroupByClause
import ch.ergon.dope.resolvable.clause.model.LetClause
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectLimitClause
import ch.ergon.dope.resolvable.clause.model.SelectOffsetClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.clause.model.WhereClause
import ch.ergon.dope.resolvable.clause.model.mergeable.JoinType
import ch.ergon.dope.resolvable.clause.model.mergeable.MergeableClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.logic.AndExpression
import ch.ergon.dope.resolvable.expression.type.logic.OrExpression
import ch.ergon.dope.resolvable.expression.type.relational.EqualsExpression

interface ClauseResolver : AbstractMongoResolver {
    fun resolve(clause: Clause): MongoDopeQuery =
        when (clause) {
            is SelectClause -> {
                val all =
                    listOf(clause.expression, *clause.expressions.toTypedArray()).map { Pair(it.toDopeQuery(this), it) }

                MongoDopeQuery(
                    queryString = "{ \$project: {" + all.joinToString(", ") {
                        when (it.second) {
                            is AliasedTypeExpression<*> -> it.first.queryString
                            else -> "${it.first.queryString}: 1"
                        }
                    } + " } }",
                    namedParams = all.map { it.first.namedParams }.merge(),
                )
            }

            is SelectDistinctClause -> {
                val all =
                    listOf(clause.expression, *clause.expressions.toTypedArray()).map { Pair(it.toDopeQuery(this), it) }

                val fieldNames = all.map { (dopeQuery, _) -> dopeQuery.queryString.trim('"') }

                val groupId = fieldNames.joinToString(", ") { "\"$it\": \"\$$it\"" }
                val projectFields = fieldNames.joinToString(", ") { "\"$it\": \"\$_id.$it\"" }

                MongoDopeQuery(
                    queryString = "{ \$group: { \"_id\": { $groupId } } },\n" +
                        "{ \$project: { $projectFields, \"_id\": 0 } }",
                    namedParams = all.map { it.first.namedParams }.merge(),
                )
            }

            is SelectRawClause<*> -> {
                val expressionDopeQuery = clause.expression.toDopeQuery(this)
                MongoDopeQuery(
                    queryString = "{ \$project: { ${expressionDopeQuery.queryString}: 1, \"_id\": 0 } }",
                    namedParams = expressionDopeQuery.namedParams,
                )
            }

            is FromClause<*> -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(this)
                val bucket = clause.fromable as? Bucket
                MongoDopeQuery(
                    queryString = parentDopeQuery.queryString,
                    bucket = bucket,
                )
            }

            is MergeableClause<*> -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(this)
                val mergeable = clause.mergeable as Bucket
                val condition = clause.condition ?: error("JOIN ON requires a condition for Mongo lookup")
                val asName = clause.bucket?.name ?: mergeable.name

                val lookup = trySimpleLookup(mergeable.name, asName, condition)
                    ?: buildPipelineLookup(mergeable.name, asName, condition)

                val unwind = if (clause.mergeType != JoinType.LEFT_JOIN) {
                    ",\n{ \$unwind: \"\$$asName\" }"
                } else {
                    ""
                }

                MongoDopeQuery(
                    queryString = "${parentDopeQuery.queryString},\n{ $lookup }$unwind",
                    bucket = parentDopeQuery.bucket,
                )
            }

            is LetClause<*> -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(this)
                val allVariables = listOf(clause.dopeVariable) + clause.dopeVariables
                val fields = allVariables.joinToString(", ") { variable ->
                    val valueDopeQuery = variable.value.toDopeQuery(this)
                    "\"${variable.name}\": ${valueDopeQuery.queryString}"
                }

                MongoDopeQuery(
                    queryString = "${parentDopeQuery.queryString},\n{ \$addFields: { $fields } }",
                    bucket = parentDopeQuery.bucket,
                )
            }

            is UnnestClause<*, *> -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(this)
                MongoDopeQuery(
                    queryString = "${parentDopeQuery.queryString},\n{ \$unwind: \"\$${clause.arrayTypeField.name}\" }",
                    bucket = parentDopeQuery.bucket,
                )
            }

            is AliasedUnnestClause<*, *> -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(this)
                val alias = clause.aliasedTypeExpression.alias
                val arrayDopeQuery = clause.aliasedTypeExpression.typeExpression.toDopeQuery(this)
                val fieldName = arrayDopeQuery.queryString.trim('"')

                MongoDopeQuery(
                    queryString = "${parentDopeQuery.queryString},\n" +
                        "{ \$unwind: \"\$$fieldName\" },\n" +
                        "{ \$addFields: { \"$alias\": \"\$$fieldName\" } }",
                    namedParams = parentDopeQuery.namedParams.merge(arrayDopeQuery.namedParams),
                    bucket = parentDopeQuery.bucket,
                )
            }

            is WhereClause -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(this)
                val whereDopeQuery = clause.whereExpression.toDopeQuery(this)
                MongoDopeQuery(
                    queryString = "{ \$match: ${whereDopeQuery.queryString} },\n${parentDopeQuery.queryString}",
                    namedParams = parentDopeQuery.namedParams.merge(whereDopeQuery.namedParams),
                    bucket = parentDopeQuery.bucket,
                )
            }

            is GroupByClause<*> -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(this)
                val allFields = listOf(clause.field) + clause.fields

                val groupId = if (allFields.size == 1) {
                    "\"\$${allFields.first().name}\""
                } else {
                    "{ " + allFields.joinToString(", ") { "\"${it.name}\": \"\$${it.name}\"" } + " }"
                }

                MongoDopeQuery(
                    queryString = "${parentDopeQuery.queryString},\n{ \$group: { \"_id\": $groupId } }",
                    bucket = parentDopeQuery.bucket,
                )
            }

            is SelectOrderByClause<*> -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(this)
                val orderExpressions = listOf(clause.orderExpression.toDopeQuery(this)) +
                    clause.additionalOrderExpressions.map { it.toDopeQuery(this) }
                MongoDopeQuery(
                    queryString = "${parentDopeQuery.queryString},\n{ \$sort: { ${orderExpressions.joinToString(", ") { it.queryString }} } }",
                    namedParams = parentDopeQuery.namedParams.merge(*orderExpressions.map { it.namedParams }
                        .toTypedArray()),
                    bucket = parentDopeQuery.bucket,
                )
            }

            is SelectOffsetClause<*> -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(this)
                val offsetDopeQuery = clause.numberExpression.toDopeQuery(this)
                MongoDopeQuery(
                    queryString = "${parentDopeQuery.queryString},\n{ \$skip: ${offsetDopeQuery.queryString} }",
                    namedParams = parentDopeQuery.namedParams.merge(offsetDopeQuery.namedParams),
                    bucket = parentDopeQuery.bucket,
                )
            }

            is SelectLimitClause<*> -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(this)
                val limitDopeQuery = clause.numberExpression.toDopeQuery(this)
                MongoDopeQuery(
                    queryString = "${parentDopeQuery.queryString},\n{ \$limit: ${limitDopeQuery.queryString} }",
                    namedParams = parentDopeQuery.namedParams.merge(limitDopeQuery.namedParams),
                    bucket = parentDopeQuery.bucket,
                )
            }

            else -> TODO("not yet implemented: $clause")
        }

    fun resolve(orderExpression: OrderExpression) : MongoDopeQuery {
        val expressionDopeQuery = orderExpression.expression.toDopeQuery(this)
        val orderTypeString = when (orderExpression.orderByType) {
            null, OrderType.ASC -> "1"
            OrderType.DESC -> "-1"
        }
        return MongoDopeQuery(
            queryString = "${expressionDopeQuery.queryString} : $orderTypeString",
            namedParams = expressionDopeQuery.namedParams,
        )
    }

    private fun trySimpleLookup(
        fromCollection: String,
        asName: String,
        condition: Expression<*>,
    ): String? {
        if (condition !is EqualsExpression<*>) return null
        val left = unwrapField(condition.left) ?: return null
        val right = unwrapField(condition.right) ?: return null
        val (localField, foreignField) = classifyFields(left, right, fromCollection) ?: return null

        return "\$lookup: {" +
            " \"from\": \"$fromCollection\"," +
            " \"localField\": \"${localField.name}\"," +
            " \"foreignField\": \"${foreignField.name}\"," +
            " \"as\": \"$asName\"" +
            " }"
    }

    private fun ClauseResolver.buildPipelineLookup(
        fromCollection: String,
        asName: String,
        condition: Expression<*>,
    ): String {
        val rendered = renderForLookupExpr(condition, fromCollection)

        val letJson = if (rendered.neededLets.isEmpty()) {
            ""
        } else {
            rendered.neededLets.joinToString(
                prefix = "\"let\": { ",
                postfix = " },",
                separator = ", ",
            ) { (originalName, varName) -> "\"$varName\": \"\$$originalName\"" }
        }

        return "\$lookup: {" +
            " \"from\": \"$fromCollection\"," +
            (if (letJson.isNotEmpty()) " $letJson" else "") +
            " \"pipeline\": [{ \"\$match\": { \"\$expr\": ${rendered.expr} } }]," +
            " \"as\": \"$asName\"" +
            " }"
    }

    private fun unwrapField(expr: Expression<*>): Field<*>? = when (expr) {
        is Field<*> -> expr
        is AliasedTypeExpression<*> -> unwrapField(expr.typeExpression)
        else -> null
    }

    private fun classifyFields(
        left: Field<*>,
        right: Field<*>,
        fromCollection: String,
    ): Pair<Field<*>, Field<*>>? {
        val leftIsForeign = left.bucket?.name == fromCollection
        val rightIsForeign = right.bucket?.name == fromCollection

        return when {
            leftIsForeign && !rightIsForeign -> right to left
            rightIsForeign && !leftIsForeign -> left to right
            !leftIsForeign && !rightIsForeign -> left to right
            else -> null
        }
    }

    private data class ExprRender(
        val expr: String,
        val neededLets: Set<Pair<String, String>>,
    )

    private fun ClauseResolver.renderForLookupExpr(
        condition: Expression<*>,
        fromCollection: String,
    ): ExprRender {
        fun go(e: Expression<*>): Pair<String, Set<Pair<String, String>>> = when (e) {
            is EqualsExpression<*> -> {
                val (l, ll) = go(e.left)
                val (r, rl) = go(e.right)
                "{ \"\$eq\": [$l, $r] }" to (ll + rl)
            }

            is AndExpression -> {
                val (l, ll) = go(e.left)
                val (r, rl) = go(e.right)
                "{ \"\$and\": [$l, $r] }" to (ll + rl)
            }

            is OrExpression -> {
                val (l, ll) = go(e.left)
                val (r, rl) = go(e.right)
                "{ \"\$or\": [$l, $r] }" to (ll + rl)
            }

            is AliasedTypeExpression<*> -> go(e.typeExpression)

            is Field<*> -> {
                val isForeign = e.bucket?.name == fromCollection
                if (isForeign) {
                    "\"\$${e.name}\"" to emptySet()
                } else {
                    val varName = "let_${e.name}"
                    "\"\$\$$varName\"" to setOf(e.name to varName)
                }
            }

            else -> {
                val dq = e.toDopeQuery(this@ClauseResolver)
                dq.queryString to emptySet()
            }
        }

        val (expr, lets) = go(condition)
        return ExprRender(expr = expr, neededLets = lets)
    }
}
