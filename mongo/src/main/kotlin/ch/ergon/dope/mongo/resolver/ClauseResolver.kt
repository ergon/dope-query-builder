package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.mongo.queryString
import ch.ergon.dope.resolvable.Selectable
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
import ch.ergon.dope.resolvable.expression.rowscope.AliasedRowScopeExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateFunctionExpression
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.logic.AndExpression
import ch.ergon.dope.resolvable.expression.type.logic.OrExpression
import ch.ergon.dope.resolvable.expression.type.relational.EqualsExpression

interface ClauseResolver : AbstractMongoResolver {
    fun resolve(clause: Clause): MongoDopeQuery =
        when (clause) {
            is SelectClause -> {
                val allSelectables = listOf(clause.expression, *clause.expressions.toTypedArray())
                if (allSelectables.none { isAggregateSelectable(it) }) {
                    val projectionEntries = allSelectables.map { projectionEntry(it) }
                    val projectsIdField = projectionEntries.any { it.queryString.trimStart().startsWith("\"_id\"") }
                    val idExclusion = if (projectsIdField) "" else ", \"_id\": 0"
                    MongoDopeQuery.Aggregation(
                        stages = listOf(
                            "{ \$project: { " + projectionEntries.joinToString(", ") { it.queryString } + idExclusion + " } }",
                        ),
                    )
                } else {
                    val aggregates = allSelectables.filter { isAggregateSelectable(it) }.map { aggregateSelection(it) }
                    val groupingFields = allSelectables.filterNot { isAggregateSelectable(it) }
                        .map { selectableFieldName(it) }
                    val accumulators = aggregates.map { "${fieldKey(it.alias)}: ${it.accumulator}" }
                    val aggregateProjections = aggregates.map { it.alias to it.projection }
                    MongoDopeQuery.Aggregation(
                        stages = buildGroupStages(groupingFields, accumulators, aggregateProjections),
                        groupSpec = MongoDopeQuery.GroupSpec(accumulators, aggregateProjections),
                    )
                }
            }

            is SelectDistinctClause -> {
                val fieldNames = (listOf(clause.expression) + clause.expressions).map { selectableFieldName(it) }

                val groupId = fieldNames.joinToString(", ") { "${fieldKey(it)}: ${fieldPath(it)}" }
                val projectFields = fieldNames.joinToString(", ") { "${fieldKey(it)}: \"\$_id.${escapeJsonString(it)}\"" }

                MongoDopeQuery.Aggregation(
                    stages = listOf(
                        "{ \$group: { \"_id\": { $groupId } } }",
                        "{ \$project: { $projectFields, \"_id\": 0 } }",
                    ),
                )
            }

            is SelectRawClause<*> -> {
                val fieldName = selectableFieldName(clause.expression)
                MongoDopeQuery.Aggregation(
                    stages = listOf("{ \$project: { ${fieldKey(fieldName)}: 1, \"_id\": 0 } }"),
                )
            }

            is FromClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                MongoDopeQuery.Aggregation(
                    stages = parent.stages,
                    bucket = clause.fromable as? Bucket
                        ?: error("Mongo requires a Bucket, got ${clause.fromable::class.simpleName}"),
                    groupSpec = parent.groupSpec,
                )
            }

            is MergeableClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                val mergeable = clause.mergeable as? Bucket
                    ?: error("Mongo requires a Bucket for JOIN, got ${clause.mergeable::class.simpleName}")
                val condition = clause.condition ?: error("JOIN ON requires a condition for Mongo lookup")
                val asName = clause.bucket?.name ?: mergeable.name

                val lookup = trySimpleLookup(mergeable.name, asName, condition)
                    ?: buildPipelineLookup(mergeable.name, asName, condition)

                val lookupStages = listOf("{ $lookup }") +
                    if (clause.mergeType != JoinType.LEFT_JOIN) listOf("{ \$unwind: \"\$$asName\" }") else emptyList()

                MongoDopeQuery.Aggregation(
                    stages = insertBeforeProjection(parent.stages, lookupStages),
                    bucket = parent.bucket,
                    groupSpec = parent.groupSpec,
                )
            }

            is LetClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                val allVariables = listOf(clause.dopeVariable) + clause.dopeVariables
                val fields = allVariables.joinToString(", ") { variable ->
                    "\"${variable.name}\": ${render(variable.value).queryString}"
                }

                MongoDopeQuery.Aggregation(
                    stages = parent.stages + "{ \$addFields: { $fields } }",
                    bucket = parent.bucket,
                    groupSpec = parent.groupSpec,
                )
            }

            is UnnestClause<*, *> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                MongoDopeQuery.Aggregation(
                    stages = parent.stages + "{ \$unwind: \"\$${clause.arrayTypeField.name}\" }",
                    bucket = parent.bucket,
                    groupSpec = parent.groupSpec,
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
                    bucket = parent.bucket,
                    groupSpec = parent.groupSpec,
                )
            }

            is SelectWhereClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                val whereDopeQuery = clause.whereExpression.toDopeQuery(this)
                MongoDopeQuery.Aggregation(
                    stages = insertBeforeProjection(
                        parent.stages,
                        listOf("{ \$match: { \"\$expr\": ${whereDopeQuery.queryString} } }"),
                    ),
                    bucket = parent.bucket,
                    groupSpec = parent.groupSpec,
                )
            }

            is GroupByClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                val groupByFields = (listOf(clause.field) + clause.fields).map { it.name }
                val spec = parent.groupSpec
                val stages = if (spec != null) {
                    val preStages = parent.stages.takeWhile { !it.startsWith("{ \$group") }
                    preStages + buildGroupStages(groupByFields, spec.accumulators, spec.aggregateProjections)
                } else {
                    val projectionIndex = parent.stages.indexOfFirst {
                        it.startsWith("{ \$group") || it.startsWith("{ \$project")
                    }
                    val preStages = if (projectionIndex == -1) parent.stages else parent.stages.take(projectionIndex)
                    preStages + buildGroupStages(groupByFields, emptyList(), emptyList())
                }
                MongoDopeQuery.Aggregation(
                    stages = stages,
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
                    bucket = parent.bucket,
                )
            }

            is SelectOffsetClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                val offsetDopeQuery = clause.numberExpression.toDopeQuery(this)
                MongoDopeQuery.Aggregation(
                    stages = parent.stages + "{ \$skip: ${offsetDopeQuery.queryString} }",
                    bucket = parent.bucket,
                )
            }

            is SelectLimitClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Aggregation
                val limitDopeQuery = clause.numberExpression.toDopeQuery(this)
                MongoDopeQuery.Aggregation(
                    stages = parent.stages + "{ \$limit: ${limitDopeQuery.queryString} }",
                    bucket = parent.bucket,
                )
            }

            is DeleteClause -> {
                MongoDopeQuery.Delete(
                    bucket = clause.deletable as? Bucket
                        ?: error("Mongo requires a Bucket for DELETE, got ${clause.deletable::class.simpleName}"),
                )
            }

            is DeleteWhereClause -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Delete
                val whereDopeQuery = clause.whereExpression.toDopeQuery(this)
                MongoDopeQuery.Delete(
                    filter = "{ \"\$expr\": ${whereDopeQuery.queryString} }",
                    bucket = parent.bucket,
                )
            }

            is UpdateClause -> {
                MongoDopeQuery.Update(
                    bucket = clause.updatable as? Bucket
                        ?: error("Mongo requires a Bucket for UPDATE, got ${clause.updatable::class.simpleName}"),
                )
            }

            is SetClause -> {
                val parent = clause.parentClause.toDopeQuery(this) as MongoDopeQuery.Update
                val allAssignments = listOf(clause.setAssignment) + clause.setAssignments
                val assignmentQueries = allAssignments.map { assignment ->
                    assignment.field.name to assignment.value.toDopeQuery(this)
                }
                val setFields = assignmentQueries.joinToString(", ") { (fieldName, valueDopeQuery) ->
                    "\"$fieldName\": ${valueDopeQuery.queryString}"
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
                    filter = "{ \"\$expr\": ${whereDopeQuery.queryString} }",
                    updateDocument = parent.updateDocument,
                    bucket = parent.bucket,
                )
            }

            else -> TODO("not yet implemented: $clause")
        }

    fun resolve(orderExpression: OrderExpression): MongoDopeQuery {
        val fieldName = selectableFieldName(orderExpression.expression)
        val orderTypeString = when (orderExpression.orderByType) {
            null, OrderType.ASC -> "1"
            OrderType.DESC -> "-1"
        }
        return MongoDopeQuery.ExpressionFragment(queryString = "${fieldKey(fieldName)}: $orderTypeString")
    }

    private fun isAggregateSelectable(selectable: Selectable): Boolean {
        val inner = when (selectable) {
            is AliasedRowScopeExpression<*> -> selectable.rowScopeExpression
            is AliasedTypeExpression<*> -> selectable.typeExpression
            else -> selectable
        }
        return inner is AggregateFunctionExpression<*>
    }

    private fun aggregateSelection(selectable: Selectable): AggregateSelection {
        val aliased = selectable as? AliasedRowScopeExpression<*>
            ?: error("Mongo requires an alias for aggregate select expressions; use .alias(\"name\").")
        val aggregate = aliased.rowScopeExpression as? AggregateFunctionExpression<*>
            ?: error("Mongo aggregate selection must wrap an aggregate function")
        val mapping = aggregateMapping(aggregate)
            ?: error("Mongo does not support this aggregate function: ${aggregate::class.simpleName}")
        val projection = mapping.projectionTemplate.replace(AGGREGATE_ALIAS_PLACEHOLDER, fieldPath(aliased.alias))
        return AggregateSelection(aliased.alias, mapping.accumulator, projection)
    }

    private data class AggregateSelection(val alias: String, val accumulator: String, val projection: String)

    private fun buildGroupStages(
        groupingFields: List<String>,
        accumulators: List<String>,
        aggregateProjections: List<Pair<String, String>>,
    ): List<String> {
        val idExpression = if (groupingFields.isEmpty()) {
            "null"
        } else {
            "{ " + groupingFields.joinToString(", ") { "${fieldKey(it)}: ${fieldPath(it)}" } + " }"
        }
        val accumulatorPart = if (accumulators.isEmpty()) "" else ", " + accumulators.joinToString(", ")
        val groupStage = "{ \$group: { \"_id\": $idExpression$accumulatorPart } }"
        val projectFields = groupingFields.map { "${fieldKey(it)}: \"\$_id.${escapeJsonString(it)}\"" } +
            aggregateProjections.map { (alias, projection) -> "${fieldKey(alias)}: $projection" }
        val projectStage = "{ \$project: { " + projectFields.joinToString(", ") + ", \"_id\": 0 } }"
        return listOf(groupStage, projectStage)
    }

    private fun insertBeforeProjection(stages: List<String>, newStages: List<String>): List<String> {
        val projectionIndex = stages.indexOfFirst { it.startsWith("{ \$group") || it.startsWith("{ \$project") }
        return if (projectionIndex == -1) {
            stages + newStages
        } else {
            stages.take(projectionIndex) + newStages + stages.drop(projectionIndex)
        }
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
        val (expression, neededLets) = renderLookupNode(condition, fromCollection)
        return ExprRender(expr = expression, neededLets = neededLets)
    }

    private fun ClauseResolver.renderLookupNode(
        expression: Expression<*>,
        fromCollection: String,
    ): Pair<String, Set<Pair<String, String>>> =
        when (expression) {
            is EqualsExpression<*> -> {
                val (left, leftLets) = renderLookupNode(expression.left, fromCollection)
                val (right, rightLets) = renderLookupNode(expression.right, fromCollection)
                "{ \"\$eq\": [$left, $right] }" to (leftLets + rightLets)
            }

            is AndExpression -> {
                val (left, leftLets) = renderLookupNode(expression.left, fromCollection)
                val (right, rightLets) = renderLookupNode(expression.right, fromCollection)
                "{ \"\$and\": [$left, $right] }" to (leftLets + rightLets)
            }

            is OrExpression -> {
                val (left, leftLets) = renderLookupNode(expression.left, fromCollection)
                val (right, rightLets) = renderLookupNode(expression.right, fromCollection)
                "{ \"\$or\": [$left, $right] }" to (leftLets + rightLets)
            }

            is AliasedTypeExpression<*> -> renderLookupNode(expression.typeExpression, fromCollection)

            is Field<*> -> {
                val isForeign = expression.bucket?.name == fromCollection
                if (isForeign) {
                    "\"\$${expression.name}\"" to emptySet()
                } else {
                    val variableName = "let_${expression.name}"
                    "\"\$\$$variableName\"" to setOf(expression.name to variableName)
                }
            }

            else -> {
                val dopeQuery = expression.toDopeQuery(this)
                dopeQuery.queryString to emptySet()
            }
        }
}
