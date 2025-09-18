package ch.ergon.dope.couchbase

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.build.QueryResolver
import ch.ergon.dope.couchbase.util.formatListToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatPartsToQueryStringWithSpace
import ch.ergon.dope.couchbase.util.formatPathToQueryString
import ch.ergon.dope.couchbase.util.formatQueryStringWithNullableFirst
import ch.ergon.dope.couchbase.util.formatStringListToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatToQueryString
import ch.ergon.dope.couchbase.util.formatToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatToQueryStringWithSeparator
import ch.ergon.dope.couchbase.util.formatToQueryStringWithSymbol
import ch.ergon.dope.merge
import ch.ergon.dope.orEmpty
import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.AliasedSelectClauseDefinition
import ch.ergon.dope.resolvable.Asterisk
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.bucket.AliasedBucketDefinition
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.bucket.IndexReference
import ch.ergon.dope.resolvable.bucket.UseIndex
import ch.ergon.dope.resolvable.bucket.UseKeysClass
import ch.ergon.dope.resolvable.clause.SetOperator
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.IndexHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysHintClass
import ch.ergon.dope.resolvable.clause.model.AliasedUnnestClause
import ch.ergon.dope.resolvable.clause.model.DeleteClause
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.GroupByClause
import ch.ergon.dope.resolvable.clause.model.LetClause
import ch.ergon.dope.resolvable.clause.model.LimitClause
import ch.ergon.dope.resolvable.clause.model.OffsetClause
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.ReturningClause
import ch.ergon.dope.resolvable.clause.model.ReturningSingleClause
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.clause.model.SetAssignment
import ch.ergon.dope.resolvable.clause.model.SetClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.clause.model.UnsetClause
import ch.ergon.dope.resolvable.clause.model.UpdateClause
import ch.ergon.dope.resolvable.clause.model.WhereClause
import ch.ergon.dope.resolvable.clause.model.WindowClause
import ch.ergon.dope.resolvable.clause.model.WindowDeclaration
import ch.ergon.dope.resolvable.clause.model.WithClause
import ch.ergon.dope.resolvable.clause.model.mergeable.JoinType
import ch.ergon.dope.resolvable.clause.model.mergeable.MergeableClause
import ch.ergon.dope.resolvable.clause.model.mergeable.NestType
import ch.ergon.dope.resolvable.clause.model.mergeable.OnType
import ch.ergon.dope.resolvable.expression.operator.InfixOperator
import ch.ergon.dope.resolvable.expression.rowscope.AliasedRowScopeExpression
import ch.ergon.dope.resolvable.expression.rowscope.RowScopeExpression
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Between
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.CurrentRow
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Following
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Preceding
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.UnboundedFollowing
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.UnboundedPreceding
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameClause
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.type.ArrayAccess
import ch.ergon.dope.resolvable.expression.type.ArrayPrimitive
import ch.ergon.dope.resolvable.expression.type.BooleanPrimitive
import ch.ergon.dope.resolvable.expression.type.CaseClass
import ch.ergon.dope.resolvable.expression.type.CaseExpression
import ch.ergon.dope.resolvable.expression.type.DopeVariable
import ch.ergon.dope.resolvable.expression.type.ElseCaseExpression
import ch.ergon.dope.resolvable.expression.type.FALSE
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.expression.type.MISSING
import ch.ergon.dope.resolvable.expression.type.MetaExpression
import ch.ergon.dope.resolvable.expression.type.MetaExpression.MetaField
import ch.ergon.dope.resolvable.expression.type.NULL
import ch.ergon.dope.resolvable.expression.type.NumberPrimitive
import ch.ergon.dope.resolvable.expression.type.ObjectEntry
import ch.ergon.dope.resolvable.expression.type.ObjectEntryPrimitive
import ch.ergon.dope.resolvable.expression.type.ObjectPrimitive
import ch.ergon.dope.resolvable.expression.type.Parameter
import ch.ergon.dope.resolvable.expression.type.SelectExpression
import ch.ergon.dope.resolvable.expression.type.StringPrimitive
import ch.ergon.dope.resolvable.expression.type.TRUE
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.arithmetic.NegationExpression
import ch.ergon.dope.resolvable.expression.type.arithmetic.NumberInfixExpression
import ch.ergon.dope.resolvable.expression.type.collection.ExistsExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.collection.SatisfiesExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayAverageExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayBinarySearchExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayContainsExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayCountExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayIfNullExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayLengthExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayMaxExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayMinExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayPositionExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayPrependExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayRangeExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayRepeatExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArraySumExpression
import ch.ergon.dope.resolvable.expression.type.function.array.UnpackExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.DecodeExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.Nvl2Expression
import ch.ergon.dope.resolvable.expression.type.function.conditional.SearchResult
import ch.ergon.dope.resolvable.expression.type.function.date.DateComponentType
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnitType
import ch.ergon.dope.resolvable.expression.type.function.numeric.NumberFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.search.ISearchFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.search.SearchDependencyFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.search.SearchFunctionType
import ch.ergon.dope.resolvable.expression.type.function.string.MaskExpression
import ch.ergon.dope.resolvable.expression.type.function.string.TokensExpression
import ch.ergon.dope.resolvable.expression.type.function.string.factory.CustomTokenOptions
import ch.ergon.dope.resolvable.expression.type.function.type.ToNumberExpression
import ch.ergon.dope.resolvable.expression.type.logic.LogicalInfixExpression
import ch.ergon.dope.resolvable.expression.type.logic.NotExpression
import ch.ergon.dope.resolvable.expression.type.range.RangeIndexedLike
import ch.ergon.dope.resolvable.expression.type.range.RangeLike
import ch.ergon.dope.resolvable.expression.type.relational.BetweenExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsMissingExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsNotMissingExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsNotNullExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsNotValuedExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsNullExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsValuedExpression
import ch.ergon.dope.resolvable.expression.type.relational.NotBetweenExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class CouchbaseResolver : QueryResolver<CouchbaseDopeQuery> {
    override fun resolve(manager: DopeQueryManager<CouchbaseDopeQuery>, resolvable: Resolvable): CouchbaseDopeQuery =
        when (resolvable) {
            is SelectClause -> {
                val parentDopeQuery = resolvable.parentClause?.toDopeQuery(manager)
                val expressionDopeQuery = resolvable.expression.toDopeQuery(manager)
                val expressionsDopeQuery = resolvable.expressions.map { it.toDopeQuery(manager) }
                CouchbaseDopeQuery(
                    queryString = formatQueryStringWithNullableFirst(
                        parentDopeQuery,
                        "SELECT",
                        expressionDopeQuery,
                        expressionsDopeQuery,
                    ),
                    parameters = (parentDopeQuery?.parameters.orEmpty()).merge(
                        expressionDopeQuery.parameters,
                        *expressionsDopeQuery.map { it.parameters }.toTypedArray(),
                    ),
                )
            }

            is UnnestClause<*, *> -> {
                val parentDopeQuery = resolvable.parentClause.toDopeQuery(manager)
                val arrayDopeQuery = resolvable.arrayTypeField.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, "UNNEST", arrayDopeQuery.queryString),
                    parameters = parentDopeQuery.parameters.merge(arrayDopeQuery.parameters),
                )
            }

            is AliasedUnnestClause<*, *> -> {
                val parent = resolvable.parentClause.toDopeQuery(manager)
                val aliased = resolvable.aliasedTypeExpression.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(parent.queryString, "UNNEST", aliased.queryString),
                    parameters = parent.parameters.merge(aliased.parameters),
                )
            }

            is SelectRawClause<*> -> {
                val parentDopeQuery = resolvable.parentClause?.toDopeQuery(manager)
                val expressionDopeQuery = resolvable.expression.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = formatQueryStringWithNullableFirst(parentDopeQuery, "SELECT RAW", expressionDopeQuery),
                    parameters = parentDopeQuery?.parameters.orEmpty().merge(expressionDopeQuery.parameters),
                )
            }

            is SelectDistinctClause -> {
                val parentDopeQuery = resolvable.parentClause?.toDopeQuery(manager)
                val expressionDopeQuery = resolvable.expression.toDopeQuery(manager)
                val additionalExpressionDopeQueries = resolvable.expressions.map { it.toDopeQuery(manager) }
                CouchbaseDopeQuery(
                    queryString = formatQueryStringWithNullableFirst(
                        parentDopeQuery,
                        "SELECT DISTINCT",
                        expressionDopeQuery,
                        additionalExpressionDopeQueries,
                    ),
                    parameters = expressionDopeQuery.parameters.merge(*additionalExpressionDopeQueries.map { it.parameters }.toTypedArray()),
                )
            }

            is FromClause<*> -> {
                val parentDopeQuery = resolvable.parentClause.toDopeQuery(manager)
                val fromableDopeQuery = when (val fromable = resolvable.fromable) {
                    is AliasedBucket -> fromable.asBucketDefinition().toDopeQuery(manager)
                    is AliasedSelectClause<*> -> fromable.asAliasedSelectClauseDefinition().toDopeQuery(manager)
                    else -> resolvable.fromable.toDopeQuery(manager)
                }
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parentDopeQuery.queryString,
                        "FROM",
                        fromableDopeQuery.queryString,
                    ),
                    parameters = parentDopeQuery.parameters.merge(fromableDopeQuery.parameters),
                )
            }

            is WhereClause -> {
                val parentDopeQuery = resolvable.parentClause.toDopeQuery(manager)
                val whereDopeQuery = resolvable.whereExpression.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parentDopeQuery.queryString,
                        "WHERE",
                        whereDopeQuery.queryString,
                    ),
                    parameters = parentDopeQuery.parameters.merge(whereDopeQuery.parameters),
                )
            }

            is GroupByClause<*> -> {
                val parent = resolvable.parentClause.toDopeQuery(manager)
                val first = resolvable.field.toDopeQuery(manager)
                val additionalFieldDopeQueries = resolvable.fields.map { it.toDopeQuery(manager) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parent.queryString,
                        "GROUP BY",
                        first.queryString,
                        *additionalFieldDopeQueries.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = parent.parameters.merge(first.parameters, *additionalFieldDopeQueries.map { it.parameters }.toTypedArray()),
                )
            }

            is SelectOrderByClause<*> -> {
                val parent = resolvable.parentClause.toDopeQuery(manager)
                val first = resolvable.orderExpression.toDopeQuery(manager)
                val additionalOrderExpressionDopeQueries = resolvable.additionalOrderExpressions.map { it.toDopeQuery(manager) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parent.queryString,
                        "ORDER BY",
                        first.queryString,
                        *additionalOrderExpressionDopeQueries.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = parent.parameters.merge(
                        first.parameters,
                        *additionalOrderExpressionDopeQueries.map { it.parameters }.toTypedArray(),
                    ),
                )
            }

            is OrderExpression -> {
                val exp = resolvable.expression.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = listOfNotNull(exp.queryString, resolvable.orderByType?.queryString).joinToString(" "),
                    parameters = exp.parameters,
                )
            }

            is LetClause<*> -> {
                val parent = resolvable.parentClause.toDopeQuery(manager)
                val first = resolvable.dopeVariable
                val firstDope = run {
                    val v = first.value.toDopeQuery(manager)
                    CouchbaseDopeQuery("`${first.name}` = ${v.queryString}", v.parameters)
                }
                val additionalVariableAssignments = resolvable.dopeVariables.map { variable ->
                    val v = variable.value.toDopeQuery(manager)
                    CouchbaseDopeQuery("`${variable.name}` = ${v.queryString}", v.parameters)
                }
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parent.queryString,
                        "LET",
                        firstDope.queryString,
                        *additionalVariableAssignments.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = parent.parameters.merge(
                        firstDope.parameters,
                        *additionalVariableAssignments.map { it.parameters }.toTypedArray(),
                    ),
                )
            }

            is WithClause -> {
                val first = resolvable.withExpression
                val firstDope = run {
                    val v = first.value.toDopeQuery(manager)
                    CouchbaseDopeQuery("`${first.name}` AS (${v.queryString})", v.parameters)
                }
                val additionalWithExpressions = resolvable.additionalWithExpressions.map { variable ->
                    val v = variable.value.toDopeQuery(manager)
                    CouchbaseDopeQuery("`${variable.name}` AS (${v.queryString})", v.parameters)
                }
                CouchbaseDopeQuery(
                    queryString = "WITH ${listOf(firstDope, *additionalWithExpressions.toTypedArray()).joinToString(", ") { it.queryString }}",
                    parameters = firstDope.parameters.merge(*additionalWithExpressions.map { it.parameters }.toTypedArray()),
                )
            }

            is ReturningClause -> {
                val parent = resolvable.parentClause.toDopeQuery(manager)
                val returnables = arrayOf(resolvable.returnable) + resolvable.additionalReturnables
                val returnableDope = returnables.map {
                    when (it) {
                        is AliasedSelectClause<*> -> it.asAliasedSelectClauseDefinition().toDopeQuery(manager)
                        else -> it.toDopeQuery(manager)
                    }
                }
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parent.queryString,
                        "RETURNING",
                        *returnableDope.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = parent.parameters.merge(*returnableDope.map { it.parameters }.toTypedArray()),
                )
            }

            is ReturningSingleClause -> {
                val parent = resolvable.parentClause.toDopeQuery(manager)
                val single = resolvable.singleReturnable.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = "${parent.queryString} RETURNING ${resolvable.returningType.name} ${single.queryString}",
                    parameters = parent.parameters.merge(single.parameters),
                )
            }

            is UpdateClause -> {
                val updatable = when (val u = resolvable.updatable) {
                    is AliasedBucket -> u.asBucketDefinition().toDopeQuery(manager)
                    else -> resolvable.updatable.toDopeQuery(manager)
                }
                CouchbaseDopeQuery(
                    queryString = "UPDATE ${updatable.queryString}",
                    parameters = updatable.parameters,
                )
            }

            is DeleteClause -> {
                val bucket = when (val d = resolvable.deletable) {
                    is AliasedBucket -> d.asBucketDefinition().toDopeQuery(manager)
                    else -> resolvable.deletable.toDopeQuery(manager)
                }
                CouchbaseDopeQuery(
                    queryString = "DELETE FROM ${bucket.queryString}",
                    parameters = bucket.parameters,
                )
            }

            is SetClause -> {
                val parentDopeQuery = resolvable.parentClause.toDopeQuery(manager)
                val firstAssignmentDopeQuery = resolvable.setAssignment.toDopeQuery(manager)
                val additionalAssignmentDopeQueries = resolvable.setAssignments.map { it.toDopeQuery(manager) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryString(
                        "${parentDopeQuery.queryString} SET",
                        firstAssignmentDopeQuery.queryString,
                        *additionalAssignmentDopeQueries.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = parentDopeQuery.parameters.merge(
                        firstAssignmentDopeQuery.parameters,
                        *additionalAssignmentDopeQueries.map { it.parameters }.toTypedArray(),
                    ),
                )
            }

            is SetAssignment<*> -> {
                val field = resolvable.field.toDopeQuery(manager)
                val value = resolvable.value.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = "${field.queryString} = ${value.queryString}",
                    parameters = field.parameters.merge(value.parameters),
                )
            }

            is UnsetClause -> {
                val parentDopeQuery = resolvable.parentClause.toDopeQuery(manager)
                val firstFieldDopeQuery = resolvable.field.toDopeQuery(manager)
                val additionalFieldDopeQueries = resolvable.fields.map { it.toDopeQuery(manager) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryString(
                        "${parentDopeQuery.queryString} UNSET",
                        firstFieldDopeQuery.queryString,
                        *additionalFieldDopeQueries.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = parentDopeQuery.parameters.merge(
                        firstFieldDopeQuery.parameters,
                        *additionalFieldDopeQueries.map { it.parameters }.toTypedArray(),
                    ),
                )
            }

            is SetOperator<*> -> {
                val left = resolvable.leftSelect.toDopeQuery(manager)
                val right = resolvable.rightSelect.toDopeQuery(manager)
                val all = if (resolvable.duplicatesAllowed) " ALL" else ""
                CouchbaseDopeQuery(
                    queryString = "(${left.queryString}) ${resolvable.setOperatorType} $all (${right.queryString})".replace("  ", " "),
                    parameters = left.parameters.merge(right.parameters),
                )
            }

            is AliasedBucket -> CouchbaseDopeQuery("`${resolvable.alias}`")

            is Bucket -> CouchbaseDopeQuery("`${resolvable.name}`")

            is AliasedBucketDefinition -> CouchbaseDopeQuery("`${resolvable.name}` AS `${resolvable.alias}`")

            is UseKeysClass -> {
                val bucket = when (val b = resolvable.bucket) {
                    is AliasedBucket -> b.asBucketDefinition().toDopeQuery(manager)
                    else -> resolvable.bucket.toDopeQuery(manager)
                }
                val keys = resolvable.useKeys.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(bucket.queryString, "USE KEYS", keys.queryString),
                    parameters = bucket.parameters.merge(keys.parameters),
                )
            }

            is IndexReference -> {
                val name = resolvable.indexName?.let { "`$it`" }
                val type = resolvable.indexType?.queryString
                CouchbaseDopeQuery(queryString = listOfNotNull(name, type).joinToString(" "))
            }

            is UseIndex -> {
                val bucket = resolvable.bucket.toDopeQuery(manager)
                val refs = resolvable.indexReferences.map { it.toDopeQuery(manager) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        bucket.queryString,
                        "USE INDEX",
                        formatListToQueryStringWithBrackets(refs, separator = ", ", prefix = "(", postfix = ")"),
                    ),
                    parameters = bucket.parameters.merge(*refs.map { it.parameters }.toTypedArray()),
                )
            }

            is NumberInfixExpression -> {
                val left = resolvable.left.toDopeQuery(manager)
                val right = resolvable.right.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    formatToQueryStringWithBrackets(left.queryString, resolvable.symbol, right.queryString),
                    left.parameters.merge(right.parameters),
                )
            }

            is LogicalInfixExpression -> {
                val left = resolvable.left.toDopeQuery(manager)
                val right = resolvable.right.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    formatToQueryStringWithBrackets(left.queryString, resolvable.symbol, right.queryString),
                    left.parameters.merge(right.parameters),
                )
            }

            is InfixOperator -> {
                val left = resolvable.left.toDopeQuery(manager)
                val right = resolvable.right.toDopeQuery(manager)
                val useBrackets = resolvable is LogicalInfixExpression || resolvable is NumberInfixExpression
                val operatorQueryString = if (useBrackets) {
                    formatToQueryStringWithBrackets(left.queryString, resolvable.symbol, right.queryString)
                } else {
                    formatToQueryStringWithSymbol(left.queryString, resolvable.symbol, right.queryString)
                }
                CouchbaseDopeQuery(operatorQueryString, left.parameters.merge(right.parameters))
            }

            is NotExpression -> {
                val arg = resolvable.argument.toDopeQuery(manager)
                CouchbaseDopeQuery(formatToQueryStringWithSeparator("NOT", separator = " ", arg.queryString), arg.parameters)
            }

            is NegationExpression -> {
                val arg = resolvable.argument.toDopeQuery(manager)
                CouchbaseDopeQuery(formatToQueryStringWithSeparator("-", separator = "", arg.queryString), arg.parameters)
            }

            is BetweenExpression<*> -> {
                val left = resolvable.expression.toDopeQuery(manager)
                val lower = resolvable.start.toDopeQuery(manager)
                val upper = resolvable.end.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = formatToQueryString(
                        left.queryString,
                        "BETWEEN ${lower.queryString}",
                        "AND ${upper.queryString}",
                        separator = " ",
                    ),
                    parameters = left.parameters.merge(lower.parameters, upper.parameters),
                )
            }

            is NotBetweenExpression<*> -> {
                val left = resolvable.expression.toDopeQuery(manager)
                val lower = resolvable.start.toDopeQuery(manager)
                val upper = resolvable.end.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = formatToQueryString(
                        left.queryString,
                        "NOT BETWEEN ${lower.queryString}",
                        "AND ${upper.queryString}",
                        separator = " ",
                    ),
                    parameters = left.parameters.merge(lower.parameters, upper.parameters),
                )
            }

            is IsNullExpression -> {
                val fieldDopeQuery = resolvable.field.toDopeQuery(manager)
                CouchbaseDopeQuery(formatToQueryString(fieldDopeQuery.queryString, "IS NULL"), fieldDopeQuery.parameters)
            }

            is IsNotNullExpression -> {
                val fieldDopeQuery = resolvable.field.toDopeQuery(manager)
                CouchbaseDopeQuery(formatToQueryString(fieldDopeQuery.queryString, "IS NOT NULL"), fieldDopeQuery.parameters)
            }

            is IsMissingExpression -> {
                val fieldDopeQuery = resolvable.field.toDopeQuery(manager)
                CouchbaseDopeQuery(formatToQueryString(fieldDopeQuery.queryString, "IS MISSING"), fieldDopeQuery.parameters)
            }

            is IsNotMissingExpression -> {
                val fieldDopeQuery = resolvable.field.toDopeQuery(manager)
                CouchbaseDopeQuery(formatToQueryString(fieldDopeQuery.queryString, "IS NOT MISSING"), fieldDopeQuery.parameters)
            }

            is IsValuedExpression -> {
                val fieldDopeQuery = resolvable.field.toDopeQuery(manager)
                CouchbaseDopeQuery(formatToQueryString(fieldDopeQuery.queryString, "IS VALUED"), fieldDopeQuery.parameters)
            }

            is IsNotValuedExpression -> {
                val fieldDopeQuery = resolvable.field.toDopeQuery(manager)
                CouchbaseDopeQuery(formatToQueryString(fieldDopeQuery.queryString, "IS NOT VALUED"), fieldDopeQuery.parameters)
            }

            is ExistsExpression<*> -> {
                val arrayDopeQuery = resolvable.array.toDopeQuery(manager)
                CouchbaseDopeQuery(queryString = "EXISTS ${arrayDopeQuery.queryString}", parameters = arrayDopeQuery.parameters)
            }

            is SatisfiesExpression<*> -> {
                val array = resolvable.arrayExpression.toDopeQuery(manager)
                val iteratorName = resolvable.iteratorName ?: manager.iteratorManager.getIteratorName()

                @Suppress("UNCHECKED_CAST")
                val predicateFunc = resolvable.predicate as (Iterator<ValidType>) -> TypeExpression<BooleanType>
                val predicate = predicateFunc(Iterator(iteratorName)).toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = "${resolvable.satisfiesType} `$iteratorName` IN ${array.queryString} SATISFIES ${predicate.queryString} END",
                    parameters = array.parameters.merge(predicate.parameters),
                )
            }

            is Iterator<*> -> CouchbaseDopeQuery("`${resolvable.variable}`")

            is AliasedTypeExpression<*> -> {
                val inner = resolvable.typeExpression.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(inner.queryString, "AS", "`${resolvable.alias}`"),
                    parameters = inner.parameters,
                )
            }

            is SelectExpression<*> -> {
                val inner = resolvable.selectClause.toDopeQuery(manager)
                CouchbaseDopeQuery("(${inner.queryString})", inner.parameters)
            }

            is ArrayAccess<*> -> {
                val arrayDopeQuery = resolvable.array.toDopeQuery(manager)
                val indexDopeQuery = resolvable.index.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    "${arrayDopeQuery.queryString}[${indexDopeQuery.queryString}]",
                    arrayDopeQuery.parameters.merge(indexDopeQuery.parameters),
                )
            }

            is ObjectEntry<*> -> {
                val objectDopeQuery = resolvable.objectExpression.toDopeQuery(manager)
                CouchbaseDopeQuery("${objectDopeQuery.queryString}.`${resolvable.key}`", objectDopeQuery.parameters)
            }

            is MetaExpression -> {
                val bucket = resolvable.bucket
                if (bucket == null) {
                    CouchbaseDopeQuery(
                        queryString = "META()",
                    )
                } else {
                    val bucketDopeQuery = bucket.toDopeQuery(manager)
                    CouchbaseDopeQuery(
                        queryString = resolvable.toFunctionQueryString(
                            symbol = "META",
                            bucketDopeQuery.queryString,
                        ),
                        parameters = bucketDopeQuery.parameters,
                    )
                }
            }

            is MetaField<*> -> {
                val meta = resolvable.metaExpression.toDopeQuery(manager)
                CouchbaseDopeQuery("${meta.queryString}.`${resolvable.name}`", meta.parameters)
            }

            is Parameter<*> -> {
                when (val name = resolvable.parameterName) {
                    null -> {
                        val placeholder = "$" + manager.parameterManager.count
                        CouchbaseDopeQuery(
                            queryString = placeholder,
                            parameters = DopeParameters(positionalParameters = listOf(resolvable.value)),
                        )
                    }

                    else -> CouchbaseDopeQuery(
                        queryString = "$$name",
                        parameters = DopeParameters(namedParameters = mapOf(name to resolvable.value)),
                    )
                }
            }

            NULL -> CouchbaseDopeQuery("NULL")
            MISSING -> CouchbaseDopeQuery("MISSING")
            TRUE -> CouchbaseDopeQuery("TRUE")
            FALSE -> CouchbaseDopeQuery("FALSE")

            is NumberPrimitive -> CouchbaseDopeQuery("${resolvable.value}")
            is StringPrimitive -> CouchbaseDopeQuery("\"${resolvable.value}\"")
            is BooleanPrimitive -> {
                when (resolvable.value) {
                    true -> CouchbaseDopeQuery("TRUE")
                    false -> CouchbaseDopeQuery("FALSE")
                }
            }

            is ArrayPrimitive<*> -> {
                val items = resolvable.collection.map { it.toDopeQuery(manager) }
                CouchbaseDopeQuery(
                    queryString = formatListToQueryStringWithBrackets(items, prefix = "[", postfix = "]"),
                    parameters = items.map { it.parameters }.merge(),
                )
            }

            is DateUnitType -> CouchbaseDopeQuery("\"${resolvable.name}\"")
            is DateComponentType -> CouchbaseDopeQuery("\"${resolvable.name}\"")

            is ObjectPrimitive -> {
                val entries = resolvable.entries.map { it.toDopeQuery(manager) }
                CouchbaseDopeQuery(
                    queryString = "{${entries.joinToString(", ") { it.queryString }}}",
                    parameters = entries.map { it.parameters }.merge(),
                )
            }

            is ObjectEntryPrimitive<*> -> {
                val key = resolvable.key.toDopeQuery(manager)
                val value = resolvable.value.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = "${key.queryString} : ${value.queryString}",
                    parameters = key.parameters.merge(value.parameters),
                )
            }

            is RangeLike<*, *> -> {
                val rangeDopeQuery = resolvable.range.toDopeQuery(manager)
                val iteratorVariable = resolvable.iteratorName ?: manager.iteratorManager.getIteratorName()
                val iteratorAny = Iterator<ValidType>(iteratorVariable)

                @Suppress("UNCHECKED_CAST")
                val withKeyExpression =
                    (resolvable.withAttributeKeys as ((Iterator<ValidType>) -> TypeExpression<StringType>)?)?.invoke(iteratorAny)

                @Suppress("UNCHECKED_CAST")
                val transformationExpression = (resolvable.transformation as (Iterator<ValidType>) -> TypeExpression<ValidType>)(iteratorAny)

                @Suppress("UNCHECKED_CAST")
                val condExpr = (resolvable.condition as ((Iterator<ValidType>) -> TypeExpression<BooleanType>)?)?.invoke(iteratorAny)
                val withAttributeKeysDopeQuery = withKeyExpression?.toDopeQuery(manager)
                val transformationDopeQuery = transformationExpression.toDopeQuery(manager)
                val conditionDopeQuery = condExpr?.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = "${resolvable.transformationType.name} " +
                        withAttributeKeysDopeQuery?.let { "${withAttributeKeysDopeQuery.queryString}:" }.orEmpty() +
                        "${transformationDopeQuery.queryString} FOR `$iteratorVariable` " +
                        "${resolvable.membershipType.name} ${rangeDopeQuery.queryString} " +
                        conditionDopeQuery?.let { "WHEN ${conditionDopeQuery.queryString} " }.orEmpty() +
                        "END",
                    parameters = rangeDopeQuery.parameters.merge(
                        withAttributeKeysDopeQuery?.parameters,
                        transformationDopeQuery.parameters,
                        conditionDopeQuery?.parameters,
                    ),
                )
            }

            is RangeIndexedLike<*, *> -> {
                val rangeQ = resolvable.range.toDopeQuery(manager)
                val indexVar = resolvable.indexName ?: manager.iteratorManager.getIteratorName()
                val iterVar = resolvable.iteratorName ?: manager.iteratorManager.getIteratorName()
                val indexIterator = Iterator<NumberType>(indexVar)
                val valueIterator = Iterator<ValidType>(iterVar)

                @Suppress("UNCHECKED_CAST")
                val withAttributeKeysExpression =
                    (resolvable.withAttributeKeys as ((Iterator<NumberType>, Iterator<ValidType>) -> TypeExpression<StringType>)?)?.invoke(
                        indexIterator,
                        valueIterator,
                    )

                @Suppress("UNCHECKED_CAST")
                val transformationExpression =
                    (resolvable.transformation as (Iterator<NumberType>, Iterator<ValidType>) -> TypeExpression<ValidType>)(
                        indexIterator,
                        valueIterator,
                    )

                @Suppress("UNCHECKED_CAST")
                val conditionExpression =
                    (resolvable.condition as ((Iterator<NumberType>, Iterator<ValidType>) -> TypeExpression<BooleanType>)?)?.invoke(
                        indexIterator,
                        valueIterator,
                    )
                val withAttributeKeysDopeQuery = withAttributeKeysExpression?.toDopeQuery(manager)
                val transformationDopeQuery = transformationExpression.toDopeQuery(manager)
                val conditionDopeQuery = conditionExpression?.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = resolvable.transformationType.name + " " +
                        (withAttributeKeysDopeQuery?.let { "${it.queryString}:" } ?: "") +
                        "${transformationDopeQuery.queryString} FOR `$indexVar`:`$iterVar` " +
                        "${resolvable.membershipType.name} ${rangeQ.queryString} " +
                        (conditionDopeQuery?.let { "WHEN ${it.queryString} " } ?: "") +
                        "END",
                    parameters = rangeQ.parameters.merge(
                        withAttributeKeysDopeQuery?.parameters,
                        transformationDopeQuery.parameters,
                        conditionDopeQuery?.parameters,
                    ),
                )
            }

            is RowScopeExpression<*> -> {
                val argumentsDopeQuery = resolvable.functionArguments.mapNotNull { it?.toDopeQuery(manager) }
                val over = resolvable.overDefinition?.toDopeQuery(manager)
                val argumentsDopeQueryString = formatListToQueryStringWithBrackets(
                    argumentsDopeQuery,
                    prefix = "(" + (resolvable.quantifier?.let { "${it.name} " } ?: ""),
                )
                val functionCallQueryString = listOfNotNull(
                    resolvable.functionName + argumentsDopeQueryString,
                    resolvable.fromModifier?.let { if (it.name == "FIRST") "FROM FIRST" else "FROM LAST" },
                    resolvable.nullsModifier?.let { if (it.name == "RESPECT") "RESPECT NULLS" else "IGNORE NULLS" },
                    over?.queryString,
                ).joinToString(" ")
                CouchbaseDopeQuery(functionCallQueryString, argumentsDopeQuery.map { it.parameters }.merge(over?.parameters))
            }

            is AliasedRowScopeExpression<*> -> {
                val inner = resolvable.rowScopeExpression.toDopeQuery(manager)
                CouchbaseDopeQuery(formatToQueryStringWithSymbol(inner.queryString, "AS", "`${resolvable.alias}`"), inner.parameters)
            }

            is OverWindowDefinition -> {
                val win = resolvable.windowDefinition.toDopeQuery(manager)
                CouchbaseDopeQuery("OVER (${win.queryString})", win.parameters)
            }

            is OverWindowReference -> CouchbaseDopeQuery("OVER `${resolvable.windowReference}`")

            is WindowDefinition -> {
                val ref = resolvable.windowReferenceExpression?.toDopeQuery(manager)
                val parts = mutableListOf<String>()
                val params = mutableListOf<DopeParameters>()
                if (ref != null) {
                    parts += ref.queryString; params += ref.parameters
                }
                resolvable.windowPartitionClause?.map { it.toDopeQuery(manager) }?.let { list ->
                    parts += list.joinToString(", ", prefix = "PARTITION BY ") { it.queryString }
                    params += list.map { it.parameters }
                }
                resolvable.windowOrderClause?.map { it.toDopeQuery(manager) }?.let { list ->
                    parts += list.joinToString(", ", prefix = "ORDER BY ") { it.queryString }
                    params += list.map { it.parameters }
                }
                val frame = resolvable.windowFrameClause?.toDopeQuery(manager)
                if (frame != null) {
                    parts += frame.queryString; params += listOf(frame.parameters)
                }
                CouchbaseDopeQuery(parts.joinToString(" "), params.merge())
            }

            is WindowFrameClause -> {
                val extent = resolvable.windowFrameExtent.toDopeQuery(manager)
                val windowFrameQueryString = listOfNotNull(
                    resolvable.windowFrameType.queryString,
                    extent.queryString,
                    resolvable.windowFrameExclusion?.queryString,
                ).joinToString(" ")
                CouchbaseDopeQuery(windowFrameQueryString, extent.parameters)
            }

            is Between -> {
                val b = resolvable.between.toDopeQuery(manager)
                val a = resolvable.and.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    formatToQueryString("BETWEEN", b.queryString, "AND", a.queryString, separator = " "),
                    b.parameters.merge(a.parameters),
                )
            }

            is UnboundedFollowing -> CouchbaseDopeQuery("UNBOUNDED FOLLOWING")

            is UnboundedPreceding -> CouchbaseDopeQuery("UNBOUNDED PRECEDING")

            is CurrentRow -> CouchbaseDopeQuery("CURRENT ROW")

            is Following -> {
                val off = resolvable.offset.toDopeQuery(manager)
                CouchbaseDopeQuery(formatToQueryString(off.queryString, "FOLLOWING"), off.parameters)
            }

            is Preceding -> {
                val off = resolvable.offset.toDopeQuery(manager)
                CouchbaseDopeQuery(formatToQueryString(off.queryString, "PRECEDING"), off.parameters)
            }

            is OrderingTerm -> {
                val expressionDopeQuery = resolvable.expression.toDopeQuery(manager)
                val orderingQueryString =
                    expressionDopeQuery.queryString + (
                        resolvable.orderType?.let { " $it" }
                            ?: ""
                        ) + (resolvable.nullsOrder?.let { " " + it.queryString } ?: "")
                CouchbaseDopeQuery(orderingQueryString, expressionDopeQuery.parameters)
            }

            is WindowClause<*> -> {
                val parent = resolvable.parentClause.toDopeQuery(manager)
                val first = resolvable.windowDeclaration.toDopeQuery(manager)
                val additionalWindowDeclarationDopeQueries = resolvable.windowDeclarations.map { it.toDopeQuery(manager) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parent.queryString,
                        "WINDOW",
                        first.queryString,
                        *additionalWindowDeclarationDopeQueries.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = parent.parameters.merge(
                        first.parameters,
                        *additionalWindowDeclarationDopeQueries.map { it.parameters }.toTypedArray(),
                    ),
                )
            }

            is WindowDeclaration -> {
                val windowDefinitionDopeQuery = resolvable.windowDefinition?.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    "`${resolvable.reference}` AS (${windowDefinitionDopeQuery?.queryString.orEmpty()})",
                    windowDefinitionDopeQuery?.parameters.orEmpty(),
                )
            }

            is MergeableClause<*> -> {
                val onType = when {
                    resolvable.condition != null -> OnType.ON
                    resolvable.keys != null || (resolvable.key != null && resolvable.bucket == null) -> OnType.ON_KEYS
                    resolvable.key != null && resolvable.bucket != null -> OnType.ON_KEY_FOR
                    else -> throw IllegalArgumentException("One of condition, keys or key must be provided for JoinClause.")
                }
                val parent = resolvable.parentClause.toDopeQuery(manager)
                val mergeable = when (val m = resolvable.mergeable) {
                    is AliasedBucket -> m.asBucketDefinition().toDopeQuery(manager)
                    is AliasedSelectClause<*> -> m.asAliasedSelectClauseDefinition().toDopeQuery(manager)
                    else -> resolvable.mergeable.toDopeQuery(manager)
                }
                val hint = if (resolvable.hashOrNestedLoopHint != null || resolvable.keysOrIndexHint != null) {
                    val h = resolvable.hashOrNestedLoopHint?.toDopeQuery(manager)
                    val k = resolvable.keysOrIndexHint?.toDopeQuery(manager)
                    CouchbaseDopeQuery(
                        formatPartsToQueryStringWithSpace("USE", h?.queryString, k?.queryString),
                        h?.parameters.orEmpty().merge(k?.parameters),
                    )
                } else {
                    null
                }
                val mergeTypeToken = when (val t = resolvable.mergeType) {
                    is JoinType -> when (t) {
                        JoinType.JOIN -> "JOIN"
                        JoinType.LEFT_JOIN -> "LEFT JOIN"
                        JoinType.INNER_JOIN -> "INNER JOIN"
                        JoinType.RIGHT_JOIN -> "RIGHT JOIN"
                    }

                    is NestType -> when (t) {
                        NestType.NEST -> "NEST"
                        NestType.INNER_NEST -> "INNER NEST"
                        NestType.LEFT_NEST -> "LEFT NEST"
                    }
                }
                val baseQueryString =
                    formatPartsToQueryStringWithSpace(parent.queryString, mergeTypeToken, mergeable.queryString, hint?.queryString)
                val baseParams = parent.parameters.merge(mergeable.parameters, hint?.parameters)
                when (onType) {
                    OnType.ON -> {
                        val cond = resolvable.condition?.toDopeQuery(manager)
                        CouchbaseDopeQuery("$baseQueryString ON ${cond?.queryString}", baseParams.merge(cond?.parameters))
                    }

                    OnType.ON_KEYS -> {
                        val keys = resolvable.keys
                        val resolvableKey = resolvable.key
                        val key = when {
                            keys != null -> keys.toDopeQuery(manager)

                            resolvableKey != null -> resolvableKey.toDopeQuery(manager)

                            else -> null
                        }
                        CouchbaseDopeQuery(
                            formatPartsToQueryStringWithSpace(baseQueryString, "ON KEYS", key?.queryString),
                            baseParams.merge(key?.parameters),
                        )
                    }

                    OnType.ON_KEY_FOR -> {
                        val key = resolvable.key?.toDopeQuery(manager)
                        val bucket = resolvable.bucket?.toDopeQuery(manager)
                        CouchbaseDopeQuery(
                            formatPartsToQueryStringWithSpace(baseQueryString, "ON KEY", key?.queryString, "FOR", bucket?.queryString),
                            baseParams.merge(key?.parameters, bucket?.parameters),
                        )
                    }
                }
            }

            is CaseClass<*> -> {
                val casePart = resolvable.case?.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = "CASE" + (casePart?.queryString?.let { " $it" } ?: ""),
                    parameters = casePart?.parameters.orEmpty(),
                )
            }

            is CaseExpression<*, *> -> {
                val case = resolvable.case.toDopeQuery(manager)
                val pairs = listOf(resolvable.firstSearchResult) + resolvable.additionalSearchResults
                val expressionDopeQueries = pairs.map { it.searchExpression.toDopeQuery(manager) to it.resultExpression.toDopeQuery(manager) }
                CouchbaseDopeQuery(
                    queryString = case.queryString +
                        expressionDopeQueries.joinToString(separator = " ", prefix = " ", postfix = " ") {
                            "WHEN ${it.first.queryString} THEN ${it.second.queryString}"
                        } + "END",
                    parameters = case.parameters.merge(
                        *expressionDopeQueries.map { it.first.parameters.merge(it.second.parameters) }
                            .toTypedArray(),
                    ),
                )
            }

            is ElseCaseExpression<*, *> -> {
                val case = resolvable.case.toDopeQuery(manager)
                val pairs = listOf(resolvable.firstSearchResult) + resolvable.additionalSearchResults
                val expressionDopeQueries = pairs.map { it.searchExpression.toDopeQuery(manager) to it.resultExpression.toDopeQuery(manager) }
                val elseDopeQuery = resolvable.elseCase.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = case.queryString +
                        expressionDopeQueries.joinToString(separator = " ", prefix = " ", postfix = " ") {
                            "WHEN ${it.first.queryString} THEN ${it.second.queryString}"
                        } + "ELSE ${elseDopeQuery.queryString} " + "END",
                    parameters = case.parameters.merge(
                        *expressionDopeQueries.map { it.first.parameters.merge(it.second.parameters) }
                            .toTypedArray(),
                    ),
                )
            }

            is IField<*> -> {
                CouchbaseDopeQuery(
                    queryString = formatPathToQueryString(resolvable.name, resolvable.path),
                )
            }

            is Asterisk -> {
                val path = resolvable.path?.toDopeQuery(manager)?.queryString
                CouchbaseDopeQuery(queryString = path?.let { "$it.*" } ?: "*")
            }

            is LimitClause -> {
                val parentDopeQuery = resolvable.parentClause.toDopeQuery(manager)
                val numberDopeQuery = resolvable.numberExpression.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parentDopeQuery.queryString,
                        "LIMIT",
                        numberDopeQuery.queryString,
                    ),
                    parameters = parentDopeQuery.parameters.merge(numberDopeQuery.parameters),
                )
            }

            is OffsetClause -> {
                val parentDopeQuery = resolvable.parentClause.toDopeQuery(manager)
                val numberDopeQuery = resolvable.numberExpression.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parentDopeQuery.queryString,
                        "OFFSET",
                        numberDopeQuery.queryString,
                    ),
                    parameters = parentDopeQuery.parameters.merge(numberDopeQuery.parameters),
                )
            }

            is FunctionExpression<*> -> {
                val argumentsDopeQuery = resolvable.expressions.mapNotNull { it?.toDopeQuery(manager) }
                CouchbaseDopeQuery(
                    queryString = resolvable.toFunctionQueryString(
                        resolvable.symbol,
                        *argumentsDopeQuery.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = argumentsDopeQuery.map { it.parameters }.merge(),
                )
            }

            is NumberFunctionExpression -> {
                val v = resolvable.value?.toDopeQuery(manager)
                val a = resolvable.additionalValue?.toDopeQuery(manager)
                val queryString = resolvable.toFunctionQueryString(resolvable.symbol, v?.queryString, a?.queryString)
                CouchbaseDopeQuery(
                    queryString = queryString,
                    parameters = v?.parameters.orEmpty().merge(a?.parameters),
                )
            }

            is ArrayFunctionExpression<*> -> {
                val arrayDopeQuery = resolvable.array.toDopeQuery(manager)
                val argumentsDopeQuery = resolvable.arguments.map { it.toDopeQuery(manager) }
                CouchbaseDopeQuery(
                    queryString = resolvable.toFunctionQueryString(
                        resolvable.symbol,
                        arrayDopeQuery.queryString,
                        *argumentsDopeQuery.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = arrayDopeQuery.parameters.merge(*argumentsDopeQuery.map { it.parameters }.toTypedArray()),
                )
            }

            is ArrayRepeatExpression<*> -> {
                val valueDopeQuery = resolvable.value.toDopeQuery(manager)
                val repetitionsDopeQuery = resolvable.repetitions.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = resolvable.toFunctionQueryString(
                        "ARRAY_REPEAT",
                        valueDopeQuery.queryString,
                        repetitionsDopeQuery.queryString,
                    ),
                    parameters = valueDopeQuery.parameters.merge(repetitionsDopeQuery.parameters),
                )
            }

            is ArrayContainsExpression<*> -> {
                val arrayDopeQuery = resolvable.array.toDopeQuery(manager)
                val valueDopeQuery = resolvable.value.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = resolvable.toFunctionQueryString(
                        "ARRAY_CONTAINS",
                        arrayDopeQuery.queryString,
                        valueDopeQuery.queryString,
                    ),
                    parameters = arrayDopeQuery.parameters.merge(valueDopeQuery.parameters),
                )
            }

            is TokensExpression -> {
                val optionsDopeQuery = resolvable.opt.toDopeQuery(manager)
                val functionQueryString = resolvable.toFunctionQueryString(
                    "TOKENS",
                    formatStringListToQueryStringWithBrackets(resolvable.inStr, prefix = "[\"", postfix = "\"]"),
                    optionsDopeQuery.queryString,
                )
                CouchbaseDopeQuery(functionQueryString, optionsDopeQuery.parameters)
            }

            is CustomTokenOptions -> CouchbaseDopeQuery(resolvable.queryString)

            is MaskExpression -> {
                val inputStringDopeQuery = resolvable.inStr.toDopeQuery(manager)
                val optionsString = "{" + resolvable.options.map { "\"${it.key}\": \"${it.value}\"" }.joinToString(", ") + "}"
                val functionQueryString =
                    resolvable.toFunctionQueryString("MASK", inputStringDopeQuery.queryString, optionsString)
                CouchbaseDopeQuery(functionQueryString, inputStringDopeQuery.parameters)
            }

            is ISearchFunctionExpression -> {
                val field = resolvable.field?.toDopeQuery(manager)
                val bucket = resolvable.bucket?.toDopeQuery(manager)
                val stringSearch =
                    resolvable.stringSearchExpression?.let { StringPrimitive(it).toDopeQuery(manager) }
                val objectSearch = resolvable.objectSearchExpression?.toDopeType()?.toDopeQuery(manager)
                val options = resolvable.options?.toDopeType()?.toDopeQuery(manager)
                val queryString = resolvable.toFunctionQueryString(
                    SearchFunctionType.SEARCH.type,
                    field?.queryString,
                    bucket?.queryString,
                    stringSearch?.queryString,
                    objectSearch?.queryString,
                    options?.queryString,
                )
                val params = field?.parameters.orEmpty().merge(bucket?.parameters, objectSearch?.parameters, options?.parameters)
                CouchbaseDopeQuery(queryString, params)
            }

            is SearchDependencyFunctionExpression<*> -> {
                val queryString = resolvable.toFunctionQueryString(
                    resolvable.searchFunctionType.type,
                    resolvable.outName?.let { "`$it`" },
                )
                CouchbaseDopeQuery(queryString)
            }

            is ToNumberExpression<*> -> {
                val expressionDopeQuery = resolvable.expression.toDopeQuery(manager)
                val filter = resolvable.filterChars?.toDopeQuery(manager)
                val queryString =
                    resolvable.toFunctionQueryString("TONUMBER", expressionDopeQuery.queryString, filter?.queryString)
                CouchbaseDopeQuery(queryString, expressionDopeQuery.parameters.merge(filter?.parameters))
            }

            is ArrayAverageExpression<*> -> {
                val arrayDopeQuery = resolvable.array.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    resolvable.toFunctionQueryString("ARRAY_AVG", arrayDopeQuery.queryString),
                    arrayDopeQuery.parameters,
                )
            }

            is ArrayMinExpression<*> -> {
                val arrayDopeQuery = resolvable.array.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    resolvable.toFunctionQueryString("ARRAY_MIN", arrayDopeQuery.queryString),
                    arrayDopeQuery.parameters,
                )
            }

            is ArrayMaxExpression<*> -> {
                val arrayDopeQuery = resolvable.array.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    resolvable.toFunctionQueryString("ARRAY_MAX", arrayDopeQuery.queryString),
                    arrayDopeQuery.parameters,
                )
            }

            is ArraySumExpression<*> -> {
                val arrayDopeQuery = resolvable.array.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    resolvable.toFunctionQueryString("ARRAY_SUM", arrayDopeQuery.queryString),
                    arrayDopeQuery.parameters,
                )
            }

            is ArrayCountExpression<*> -> {
                val arrayDopeQuery = resolvable.array.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    resolvable.toFunctionQueryString("ARRAY_COUNT", arrayDopeQuery.queryString),
                    arrayDopeQuery.parameters,
                )
            }

            is ArrayLengthExpression<*> -> {
                val arrayDopeQuery = resolvable.array.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    resolvable.toFunctionQueryString("ARRAY_LENGTH", arrayDopeQuery.queryString),
                    arrayDopeQuery.parameters,
                )
            }

            is ArrayPositionExpression<*> -> {
                val arrayDopeQuery = resolvable.array.toDopeQuery(manager)
                val valueDopeQuery = resolvable.value.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    resolvable.toFunctionQueryString(
                        "ARRAY_POSITION",
                        arrayDopeQuery.queryString,
                        valueDopeQuery.queryString,
                    ),
                    arrayDopeQuery.parameters.merge(valueDopeQuery.parameters),
                )
            }

            is ArrayBinarySearchExpression<*> -> {
                val arrayDopeQuery = resolvable.array.toDopeQuery(manager)
                val valueDopeQuery = resolvable.value.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    resolvable.toFunctionQueryString(
                        "ARRAY_BINARY_SEARCH",
                        arrayDopeQuery.queryString,
                        valueDopeQuery.queryString,
                    ),
                    arrayDopeQuery.parameters.merge(valueDopeQuery.parameters),
                )
            }

            is ArrayIfNullExpression<*> -> {
                val arrayDopeQuery = resolvable.array.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    resolvable.toFunctionQueryString("ARRAY_IFNULL", arrayDopeQuery.queryString),
                    arrayDopeQuery.parameters,
                )
            }

            is ArrayPrependExpression<*> -> {
                val arrayDopeQuery = resolvable.array.toDopeQuery(manager)
                val valueDopeQuery = resolvable.value.toDopeQuery(manager)
                val additionalValueDopeQueries = resolvable.additionalValues.map { it.toDopeQuery(manager) }
                val functionQueryString = resolvable.toFunctionQueryString(
                    "ARRAY_PREPEND",
                    valueDopeQuery.queryString,
                    *additionalValueDopeQueries.map { it.queryString }.toTypedArray(),
                    arrayDopeQuery.queryString,
                )
                CouchbaseDopeQuery(
                    functionQueryString,
                    arrayDopeQuery.parameters.merge(valueDopeQuery.parameters, *additionalValueDopeQueries.map { it.parameters }.toTypedArray()),
                )
            }

            is ArrayRangeExpression -> {
                val startDopeQuery = resolvable.start.toDopeQuery(manager)
                val endDopeQuery = resolvable.end.toDopeQuery(manager)
                val step = resolvable.step?.toDopeQuery(manager)
                val functionQueryString = resolvable.toFunctionQueryString(
                    "ARRAY_RANGE",
                    startDopeQuery.queryString,
                    endDopeQuery.queryString,
                    step?.queryString,
                )
                CouchbaseDopeQuery(functionQueryString, startDopeQuery.parameters.merge(endDopeQuery.parameters, step?.parameters))
            }

            is UnpackExpression -> {
                val objectDopeQuery = resolvable.objectArray.toDopeQuery(manager)
                CouchbaseDopeQuery("${objectDopeQuery.queryString}[*]", objectDopeQuery.parameters)
            }

            is Nvl2Expression<*> -> {
                val initialExpressionDopeQuery = resolvable.initialExpression.toDopeQuery(manager)
                val valueIfExistsDopeQuery = resolvable.valueIfExists.toDopeQuery(manager)
                val valueIfNotExistsDopeQuery = resolvable.valueIfNotExists.toDopeQuery(manager)
                val functionQueryString = resolvable.toFunctionQueryString(
                    "NVL2",
                    initialExpressionDopeQuery.queryString,
                    valueIfExistsDopeQuery.queryString,
                    valueIfNotExistsDopeQuery.queryString,
                )
                CouchbaseDopeQuery(
                    functionQueryString,
                    initialExpressionDopeQuery.parameters.merge(valueIfExistsDopeQuery.parameters, valueIfNotExistsDopeQuery.parameters),
                )
            }

            is DecodeExpression<*, *> -> {
                val decodeExpressionDopeQuery = resolvable.decodeExpression.toDopeQuery(manager)
                fun pair(searchResult: SearchResult<*, *>): CouchbaseDopeQuery {
                    val searchExpressionDopeQuery =
                        searchResult.searchExpression.toDopeQuery(manager)
                    val resultExpressionDopeQuery = searchResult.resultExpression.toDopeQuery(manager)
                    return CouchbaseDopeQuery(
                        "${searchExpressionDopeQuery.queryString}, ${resultExpressionDopeQuery.queryString}",
                        searchExpressionDopeQuery.parameters.merge(resultExpressionDopeQuery.parameters),
                    )
                }

                val firstPairDopeQuery = pair(resolvable.searchResult)
                val additionalPairDopeQueries = resolvable.searchResults.map { pair(it) }
                val defaultDopeQuery = resolvable.default?.toDopeQuery(manager)
                val functionQueryString = resolvable.toFunctionQueryString(
                    "DECODE",
                    decodeExpressionDopeQuery.queryString,
                    firstPairDopeQuery.queryString,
                    *additionalPairDopeQueries.map { it.queryString }.toTypedArray(),
                    defaultDopeQuery?.queryString,
                )
                val mergedParameters = decodeExpressionDopeQuery.parameters.merge(
                    firstPairDopeQuery.parameters,
                    *additionalPairDopeQueries.map { it.parameters }.toTypedArray(),
                    defaultDopeQuery?.parameters,
                )
                CouchbaseDopeQuery(functionQueryString, mergedParameters)
            }

            is HashOrNestedLoopHint -> when (resolvable) {
                HashOrNestedLoopHint.HASH_BUILD -> CouchbaseDopeQuery("HASH (BUILD)")
                HashOrNestedLoopHint.HASH_PROBE -> CouchbaseDopeQuery("HASH (PROBE)")
                HashOrNestedLoopHint.NESTED_LOOP -> CouchbaseDopeQuery("NL")
            }

            is KeysHintClass -> {
                val keys = resolvable.keys.toDopeQuery(manager)
                CouchbaseDopeQuery(queryString = "KEYS ${keys.queryString}", parameters = keys.parameters)
            }

            is IndexHint -> {
                val refs = resolvable.indexReferences.map { it.toDopeQuery(manager) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryString("INDEX", formatListToQueryStringWithBrackets(refs)),
                    parameters = refs.map { it.parameters }.merge(),
                )
            }

            is DopeVariable<*> -> CouchbaseDopeQuery("`${resolvable.name}`")

            is AliasedSelectClause<*> -> CouchbaseDopeQuery("`${resolvable.alias}`")

            is AliasedSelectClauseDefinition<*> -> {
                val parent = resolvable.parentClause.toDopeQuery(manager)
                CouchbaseDopeQuery("(${parent.queryString}) AS `${resolvable.alias}`", parent.parameters)
            }

            else -> TODO("not yet implemented")
        }
}
