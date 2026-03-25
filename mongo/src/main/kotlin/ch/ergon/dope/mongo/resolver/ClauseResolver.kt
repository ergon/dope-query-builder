package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.merge
import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.mongo.queryString
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.model.AliasedUnnestClause
import ch.ergon.dope.resolvable.clause.model.DeleteClause
import ch.ergon.dope.resolvable.clause.model.DeleteWhereClause
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
import ch.ergon.dope.resolvable.clause.model.SelectWhereClause
import ch.ergon.dope.resolvable.clause.model.SetClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.clause.model.UnsetClause
import ch.ergon.dope.resolvable.clause.model.UpdateClause
import ch.ergon.dope.resolvable.clause.model.UpdateWhereClause
import ch.ergon.dope.resolvable.clause.model.mergeable.JoinType
import ch.ergon.dope.resolvable.clause.model.mergeable.MergeableClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.expression.type.logic.AndExpression
import ch.ergon.dope.resolvable.expression.type.logic.OrExpression
import ch.ergon.dope.resolvable.expression.type.relational.EqualsExpression

interface ClauseResolver : AbstractMongoResolver {
    fun resolve(clause: Clause): MongoDopeQuery =
        when (clause) {
            is SelectClause -> {
                val all =
                    listOf(clause.expression, *clause.expressions.toTypedArray()).map { Pair(it.toDopeQuery(this), it) }

                MongoDopeQuery.Aggregation(
                    stages = listOf(
                        "{ \$project: {" + all.joinToString(", ") {
                            when (it.second) {
                                is AliasedTypeExpression<*> -> it.first.queryString
                                else -> "${it.first.queryString}: 1"
                            }
                        } + " } }",
                    ),
                    namedParameters = all.map { it.first.namedParameters }.merge(),
                )
            }

            is SelectDistinctClause -> {
                val all =
                    listOf(clause.expression, *clause.expressions.toTypedArray()).map { Pair(it.toDopeQuery(this), it) }

                val fieldNames = all.map { (dopeQuery, _) -> dopeQuery.queryString.trim('"') }

                val groupId = fieldNames.joinToString(", ") { "\"$it\": \"\$$it\"" }
                val projectFields = fieldNames.joinToString(", ") { "\"$it\": \"\$_id.$it\"" }

                MongoDopeQuery.Aggregation(
                    stages = listOf(
                        "{ \$group: { \"_id\": { $groupId } } }",
                        "{ \$project: { $projectFields, \"_id\": 0 } }",
                    ),
                    namedParameters = all.map { it.first.namedParameters }.merge(),
                )
            }

            is SelectRawClause<*> -> {
                val expressionDopeQuery = clause.expression.toDopeQuery(this)
                MongoDopeQuery.Aggregation(
                    stages = listOf("{ \$project: { ${expressionDopeQuery.queryString}: 1, \"_id\": 0 } }"),
                    namedParameters = expressionDopeQuery.namedParameters,
                )
            }

            is FromClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                MongoDopeQuery.Aggregation(
                    stages = parent.stages,
                    bucket = clause.fromable as? Bucket,
                )
            }

            is MergeableClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                val mergeable = clause.mergeable as Bucket
                val condition = clause.condition ?: error("JOIN ON requires a condition for Mongo lookup")
                val asName = clause.bucket?.name ?: mergeable.name

                val lookup = trySimpleLookup(mergeable.name, asName, condition)
                    ?: buildPipelineLookup(mergeable.name, asName, condition)

                val lookupStages = listOf("{ $lookup }") +
                    if (clause.mergeType != JoinType.LEFT_JOIN) listOf("{ \$unwind: \"\$$asName\" }") else emptyList()

                MongoDopeQuery.Aggregation(
                    stages = parent.stages + lookupStages,
                    bucket = parent.bucket,
                )
            }

            is LetClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                val allVariables = listOf(clause.dopeVariable) + clause.dopeVariables
                val fields = allVariables.joinToString(", ") { variable ->
                    val value = if (variable.value is IField<*>) {
                        "\"\$${(variable.value as IField<*>).name}\""
                    } else {
                        variable.value.toDopeQuery(this).queryString
                    }
                    "\"${variable.name}\": $value"
                }

                MongoDopeQuery.Aggregation(
                    stages = parent.stages + "{ \$addFields: { $fields } }",
                    bucket = parent.bucket,
                )
            }

            is UnnestClause<*, *> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                MongoDopeQuery.Aggregation(
                    stages = parent.stages + "{ \$unwind: \"\$${clause.arrayTypeField.name}\" }",
                    bucket = parent.bucket,
                )
            }

            is AliasedUnnestClause<*, *> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                val alias = clause.aliasedTypeExpression.alias
                val arrayDopeQuery = clause.aliasedTypeExpression.typeExpression.toDopeQuery(this)
                val fieldName = arrayDopeQuery.queryString.trim('"')

                MongoDopeQuery.Aggregation(
                    stages = parent.stages + listOf(
                        "{ \$unwind: \"\$$fieldName\" }",
                        "{ \$addFields: { \"$alias\": \"\$$fieldName\" } }",
                    ),
                    namedParameters = parent.namedParameters.merge(arrayDopeQuery.namedParameters),
                    bucket = parent.bucket,
                )
            }

            is SelectWhereClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                val whereDopeQuery = clause.whereExpression.toDopeQuery(this)
                MongoDopeQuery.Aggregation(
                    stages = listOf("{ \$match: ${whereDopeQuery.queryString} }") + parent.stages,
                    namedParameters = parent.namedParameters.merge(whereDopeQuery.namedParameters),
                    bucket = parent.bucket,
                )
            }

            is GroupByClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                val allFields = listOf(clause.field) + clause.fields

                val groupId = if (allFields.size == 1) {
                    "\"\$${allFields.first().name}\""
                } else {
                    "{ " + allFields.joinToString(", ") { "\"${it.name}\": \"\$${it.name}\"" } + " }"
                }

                MongoDopeQuery.Aggregation(
                    stages = parent.stages + "{ \$group: { \"_id\": $groupId } }",
                    bucket = parent.bucket,
                )
            }

            is SelectOrderByClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                val orderExpressions = listOf(clause.orderExpression.toDopeQuery(this)) +
                    clause.additionalOrderExpressions.map { it.toDopeQuery(this) }
                MongoDopeQuery.Aggregation(
                    stages = parent.stages +
                        "{ \$sort: { ${orderExpressions.joinToString(", ") { it.queryString }} } }",
                    namedParameters = parent.namedParameters.merge(
                        *orderExpressions.map { it.namedParameters }.toTypedArray(),
                    ),
                    bucket = parent.bucket,
                )
            }

            is SelectOffsetClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                val offsetDopeQuery = clause.numberExpression.toDopeQuery(this)
                MongoDopeQuery.Aggregation(
                    stages = parent.stages + "{ \$skip: ${offsetDopeQuery.queryString} }",
                    namedParameters = parent.namedParameters.merge(offsetDopeQuery.namedParameters),
                    bucket = parent.bucket,
                )
            }

            is SelectLimitClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                val limitDopeQuery = clause.numberExpression.toDopeQuery(this)
                MongoDopeQuery.Aggregation(
                    stages = parent.stages + "{ \$limit: ${limitDopeQuery.queryString} }",
                    namedParameters = parent.namedParameters.merge(limitDopeQuery.namedParameters),
                    bucket = parent.bucket,
                )
            }

            is DeleteClause -> {
                MongoDopeQuery.Delete(bucket = clause.deletable as Bucket)
            }

            is DeleteWhereClause -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Delete
                val whereDopeQuery = clause.whereExpression.toDopeQuery(this)
                MongoDopeQuery.Delete(
                    filter = whereDopeQuery.queryString,
                    namedParameters = parent.namedParameters.merge(whereDopeQuery.namedParameters),
                    bucket = parent.bucket,
                )
            }

            is UpdateClause -> {
                MongoDopeQuery.Update(bucket = clause.updatable as Bucket)
            }

            is SetClause -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Update
                val allAssignments = listOf(clause.setAssignment) + clause.setAssignments
                val setFields = allAssignments.joinToString(", ") { assignment ->
                    val valueDopeQuery = assignment.value.toDopeQuery(this)
                    "\"${assignment.field.name}\": ${valueDopeQuery.queryString}"
                }

                MongoDopeQuery.Update(
                    updateDocument = mergeUpdateOperators(
                        parent.updateDocument,
                        "\"\$set\": { $setFields }",
                    ),
                    bucket = parent.bucket,
                )
            }

            is UnsetClause -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Update
                val allFields = listOf(clause.field) + clause.fields
                val unsetFields = allFields.joinToString(", ") { "\"${it.name}\": \"\"" }

                MongoDopeQuery.Update(
                    updateDocument = mergeUpdateOperators(
                        parent.updateDocument,
                        "\"\$unset\": { $unsetFields }",
                    ),
                    bucket = parent.bucket,
                )
            }

            is UpdateWhereClause -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Update
                val whereDopeQuery = clause.whereExpression.toDopeQuery(this)
                MongoDopeQuery.Update(
                    filter = whereDopeQuery.queryString,
                    updateDocument = parent.updateDocument,
                    namedParameters = parent.namedParameters.merge(whereDopeQuery.namedParameters),
                    bucket = parent.bucket,
                )
            }

            else -> TODO("not yet implemented: $clause")
        }

    fun resolve(orderExpression: OrderExpression): MongoDopeQuery {
        val expressionDopeQuery = orderExpression.expression.toDopeQuery(this)
        val orderTypeString = when (orderExpression.orderByType) {
            null, OrderType.ASC -> "1"
            OrderType.DESC -> "-1"
        }
        return MongoDopeQuery.ExpressionFragment(
            queryString = "${expressionDopeQuery.queryString} : $orderTypeString",
            namedParameters = expressionDopeQuery.namedParameters,
        )
    }

    private fun mergeUpdateOperators(existing: String, newOperator: String): String {
        if (existing == "{}") return "{ $newOperator }"
        return existing.trimEnd().dropLast(1).trimEnd() + ", $newOperator }"
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
            !leftIsForeign -> left to right
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
