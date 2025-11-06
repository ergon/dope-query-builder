package ch.ergon.dope.couchbase

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.couchbase.util.formatListToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatToQueryString
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
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.IndexHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysHintClass
import ch.ergon.dope.resolvable.clause.model.LimitClause
import ch.ergon.dope.resolvable.clause.model.OffsetClause
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.SetAssignment
import ch.ergon.dope.resolvable.clause.model.WindowDeclaration
import ch.ergon.dope.resolvable.clause.model.WithClause
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
import ch.ergon.dope.resolvable.expression.type.CaseClass
import ch.ergon.dope.resolvable.expression.type.ObjectEntryPrimitive
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.function.string.factory.CustomTokenOptions
import ch.ergon.dope.resolvable.expression.type.range.RangeIndexedLike
import ch.ergon.dope.resolvable.expression.type.range.RangeLike
import ch.ergon.dope.resolver.QueryResolver
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class CouchbaseResolver(override val manager: DopeQueryManager = DopeQueryManager()) : QueryResolver<CouchbaseDopeQuery> {
    override fun resolve(resolvable: Resolvable): CouchbaseDopeQuery =
        when (resolvable) {
            is Clause -> ClauseResolver.resolve(this, resolvable)

            is RangeLike<*, *> -> {
                val rangeDopeQuery = resolvable.range.toDopeQuery(this)
                val iteratorVariable = resolvable.iteratorName ?: manager.iteratorManager.getIteratorName()
                val iteratorAny = Iterator<ValidType>(iteratorVariable)

                @Suppress("UNCHECKED_CAST")
                val withKeyExpression =
                    (resolvable.withAttributeKeys as ((Iterator<ValidType>) -> TypeExpression<StringType>)?)?.invoke(iteratorAny)

                @Suppress("UNCHECKED_CAST")
                val transformationExpression = (resolvable.transformation as (Iterator<ValidType>) -> TypeExpression<ValidType>)(iteratorAny)

                @Suppress("UNCHECKED_CAST")
                val condExpr = (resolvable.condition as ((Iterator<ValidType>) -> TypeExpression<BooleanType>)?)?.invoke(iteratorAny)
                val withAttributeKeysDopeQuery = withKeyExpression?.toDopeQuery(this)
                val transformationDopeQuery = transformationExpression.toDopeQuery(this)
                val conditionDopeQuery = condExpr?.toDopeQuery(this)
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
                val rangeQ = resolvable.range.toDopeQuery(this)
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
                val withAttributeKeysDopeQuery = withAttributeKeysExpression?.toDopeQuery(this)
                val transformationDopeQuery = transformationExpression.toDopeQuery(this)
                val conditionDopeQuery = conditionExpression?.toDopeQuery(this)
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

            is InfixOperator -> InfixOperatorResolver.resolve(this, resolvable)

            is TypeExpression<*> -> TypeExpressionResolver.resolve(this, resolvable)

            is WithClause -> {
                val first = resolvable.withExpression
                val firstDope = first.toWithDefinitionDopeQuery(this)
                val additionalWithExpressions = resolvable.additionalWithExpressions.map { variable ->
                    variable.toWithDefinitionDopeQuery(this)
                }
                CouchbaseDopeQuery(
                    queryString = "WITH ${listOf(firstDope, *additionalWithExpressions.toTypedArray()).joinToString(", ") { it.queryString }}",
                    parameters = firstDope.parameters.merge(*additionalWithExpressions.map { it.parameters }.toTypedArray()),
                )
            }

            is OrderExpression -> {
                val exp = resolvable.expression.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = listOfNotNull(exp.queryString, resolvable.orderByType?.queryString).joinToString(" "),
                    parameters = exp.parameters,
                )
            }

            is SetAssignment<*> -> {
                val field = resolvable.field.toDopeQuery(this)
                val value = resolvable.value.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = "${field.queryString} = ${value.queryString}",
                    parameters = field.parameters.merge(value.parameters),
                )
            }

            is AliasedBucket -> CouchbaseDopeQuery("`${resolvable.alias}`")

            is Bucket -> CouchbaseDopeQuery("`${resolvable.name}`")

            is AliasedBucketDefinition -> CouchbaseDopeQuery("`${resolvable.name}` AS `${resolvable.alias}`")

            is UseKeysClass -> {
                val bucket = when (val b = resolvable.bucket) {
                    is AliasedBucket -> b.asBucketDefinition().toDopeQuery(this)
                    else -> resolvable.bucket.toDopeQuery(this)
                }
                val keys = resolvable.useKeys.toDopeQuery(this)
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
                val bucket = resolvable.bucket.toDopeQuery(this)
                val refs = resolvable.indexReferences.map { it.toDopeQuery(this) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        bucket.queryString,
                        "USE INDEX",
                        formatListToQueryStringWithBrackets(refs, separator = ", ", prefix = "(", postfix = ")"),
                    ),
                    parameters = bucket.parameters.merge(*refs.map { it.parameters }.toTypedArray()),
                )
            }

            is AliasedTypeExpression<*> -> {
                val inner = resolvable.typeExpression.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(inner.queryString, "AS", "`${resolvable.alias}`"),
                    parameters = inner.parameters,
                )
            }

            is ObjectEntryPrimitive<*> -> {
                val key = resolvable.key.toDopeQuery(this)
                val value = resolvable.value.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = "${key.queryString} : ${value.queryString}",
                    parameters = key.parameters.merge(value.parameters),
                )
            }

            is RowScopeExpression<*> -> {
                val argumentsDopeQuery = resolvable.functionArguments.mapNotNull { it?.toDopeQuery(this) }
                val over = resolvable.overDefinition?.toDopeQuery(this)
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
                val inner = resolvable.rowScopeExpression.toDopeQuery(this)
                CouchbaseDopeQuery(formatToQueryStringWithSymbol(inner.queryString, "AS", "`${resolvable.alias}`"), inner.parameters)
            }

            is OverWindowDefinition -> {
                val win = resolvable.windowDefinition.toDopeQuery(this)
                CouchbaseDopeQuery("OVER (${win.queryString})", win.parameters)
            }

            is OverWindowReference -> CouchbaseDopeQuery("OVER `${resolvable.windowReference}`")

            is WindowDefinition -> {
                val ref = resolvable.windowReferenceExpression?.toDopeQuery(this)
                val parts = mutableListOf<String>()
                val params = mutableListOf<DopeParameters>()
                if (ref != null) {
                    parts += ref.queryString; params += ref.parameters
                }
                resolvable.windowPartitionClause?.map { it.toDopeQuery(this) }?.let { list ->
                    parts += list.joinToString(", ", prefix = "PARTITION BY ") { it.queryString }
                    params += list.map { it.parameters }
                }
                resolvable.windowOrderClause?.map { it.toDopeQuery(this) }?.let { list ->
                    parts += list.joinToString(", ", prefix = "ORDER BY ") { it.queryString }
                    params += list.map { it.parameters }
                }
                val frame = resolvable.windowFrameClause?.toDopeQuery(this)
                if (frame != null) {
                    parts += frame.queryString; params += listOf(frame.parameters)
                }
                CouchbaseDopeQuery(parts.joinToString(" "), params.merge())
            }

            is WindowFrameClause -> {
                val extent = resolvable.windowFrameExtent.toDopeQuery(this)
                val windowFrameQueryString = listOfNotNull(
                    resolvable.windowFrameType.queryString,
                    extent.queryString,
                    resolvable.windowFrameExclusion?.queryString,
                ).joinToString(" ")
                CouchbaseDopeQuery(windowFrameQueryString, extent.parameters)
            }

            is Between -> {
                val b = resolvable.between.toDopeQuery(this)
                val a = resolvable.and.toDopeQuery(this)
                CouchbaseDopeQuery(
                    formatToQueryString("BETWEEN", b.queryString, "AND", a.queryString, separator = " "),
                    b.parameters.merge(a.parameters),
                )
            }

            is UnboundedFollowing -> CouchbaseDopeQuery("UNBOUNDED FOLLOWING")

            is UnboundedPreceding -> CouchbaseDopeQuery("UNBOUNDED PRECEDING")

            is CurrentRow -> CouchbaseDopeQuery("CURRENT ROW")

            is Following -> {
                val off = resolvable.offset.toDopeQuery(this)
                CouchbaseDopeQuery(formatToQueryString(off.queryString, "FOLLOWING"), off.parameters)
            }

            is Preceding -> {
                val off = resolvable.offset.toDopeQuery(this)
                CouchbaseDopeQuery(formatToQueryString(off.queryString, "PRECEDING"), off.parameters)
            }

            is OrderingTerm -> {
                val expressionDopeQuery = resolvable.expression.toDopeQuery(this)
                val orderingQueryString =
                    expressionDopeQuery.queryString + (
                        resolvable.orderType?.let { " $it" }
                            ?: ""
                        ) + (resolvable.nullsOrder?.let { " " + it.queryString } ?: "")
                CouchbaseDopeQuery(orderingQueryString, expressionDopeQuery.parameters)
            }

            is WindowDeclaration -> {
                val windowDefinitionDopeQuery = resolvable.windowDefinition?.toDopeQuery(this)
                CouchbaseDopeQuery(
                    "`${resolvable.reference}` AS (${windowDefinitionDopeQuery?.queryString.orEmpty()})",
                    windowDefinitionDopeQuery?.parameters.orEmpty(),
                )
            }

            is CaseClass<*> -> {
                val casePart = resolvable.case?.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = "CASE" + (casePart?.queryString?.let { " $it" } ?: ""),
                    parameters = casePart?.parameters.orEmpty(),
                )
            }

            is Asterisk -> {
                val path = resolvable.path?.toDopeQuery(this)?.queryString
                CouchbaseDopeQuery(queryString = path?.let { "$it.*" } ?: "*")
            }

            is LimitClause -> {
                val parentDopeQuery = resolvable.parentClause.toDopeQuery(this)
                val numberDopeQuery = resolvable.numberExpression.toDopeQuery(this)
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
                val parentDopeQuery = resolvable.parentClause.toDopeQuery(this)
                val numberDopeQuery = resolvable.numberExpression.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parentDopeQuery.queryString,
                        "OFFSET",
                        numberDopeQuery.queryString,
                    ),
                    parameters = parentDopeQuery.parameters.merge(numberDopeQuery.parameters),
                )
            }

            is CustomTokenOptions -> CouchbaseDopeQuery(resolvable.queryString)

            is HashOrNestedLoopHint -> when (resolvable) {
                HashOrNestedLoopHint.HASH_BUILD -> CouchbaseDopeQuery("HASH (BUILD)")
                HashOrNestedLoopHint.HASH_PROBE -> CouchbaseDopeQuery("HASH (PROBE)")
                HashOrNestedLoopHint.NESTED_LOOP -> CouchbaseDopeQuery("NL")
            }

            is KeysHintClass -> {
                val keys = resolvable.keys.toDopeQuery(this)
                CouchbaseDopeQuery(queryString = "KEYS ${keys.queryString}", parameters = keys.parameters)
            }

            is IndexHint -> {
                val refs = resolvable.indexReferences.map { it.toDopeQuery(this) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryString("INDEX", formatListToQueryStringWithBrackets(refs)),
                    parameters = refs.map { it.parameters }.merge(),
                )
            }

            is AliasedSelectClause<*> -> CouchbaseDopeQuery("`${resolvable.alias}`")

            is AliasedSelectClauseDefinition<*> -> {
                val parent = resolvable.parentClause.toDopeQuery(this)
                CouchbaseDopeQuery("(${parent.queryString}) AS `${resolvable.alias}`", parent.parameters)
            }

            else -> throw UnsupportedOperationException("Not supported: $resolvable")
        }
}
