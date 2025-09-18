package ch.ergon.dope.couchbase

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.build.QueryResolver
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
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

object CouchbaseResolver : QueryResolver<CouchbaseDopeQuery> {
    override fun resolve(manager: DopeQueryManager<CouchbaseDopeQuery>, resolvable: Resolvable): CouchbaseDopeQuery =
        when (resolvable) {
            is Clause -> ClauseResolver.resolve(manager, resolvable)

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

            is InfixOperator -> InfixOperatorResolver.resolve(manager, resolvable)

            is TypeExpression<*> -> TypeExpressionResolver.resolve(manager, resolvable)

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

            is OrderExpression -> {
                val exp = resolvable.expression.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = listOfNotNull(exp.queryString, resolvable.orderByType?.queryString).joinToString(" "),
                    parameters = exp.parameters,
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

            is AliasedTypeExpression<*> -> {
                val inner = resolvable.typeExpression.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(inner.queryString, "AS", "`${resolvable.alias}`"),
                    parameters = inner.parameters,
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

            is WindowDeclaration -> {
                val windowDefinitionDopeQuery = resolvable.windowDefinition?.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    "`${resolvable.reference}` AS (${windowDefinitionDopeQuery?.queryString.orEmpty()})",
                    windowDefinitionDopeQuery?.parameters.orEmpty(),
                )
            }

            is CaseClass<*> -> {
                val casePart = resolvable.case?.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    queryString = "CASE" + (casePart?.queryString?.let { " $it" } ?: ""),
                    parameters = casePart?.parameters.orEmpty(),
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

            is CustomTokenOptions -> CouchbaseDopeQuery(resolvable.queryString)

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

            is AliasedSelectClause<*> -> CouchbaseDopeQuery("`${resolvable.alias}`")

            is AliasedSelectClauseDefinition<*> -> {
                val parent = resolvable.parentClause.toDopeQuery(manager)
                CouchbaseDopeQuery("(${parent.queryString}) AS `${resolvable.alias}`", parent.parameters)
            }

            else -> TODO("not yet implemented: $resolvable")
        }
}
