package ch.ergon.dope.couchbase

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.resolvable.expression.type.MetaExpression
import ch.ergon.dope.couchbase.resolvable.expression.type.MetaExpression.MetaField
import ch.ergon.dope.couchbase.util.formatListToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatPathToQueryString
import ch.ergon.dope.couchbase.util.formatStringListToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatToQueryString
import ch.ergon.dope.couchbase.util.formatToQueryStringWithSeparator
import ch.ergon.dope.merge
import ch.ergon.dope.orEmpty
import ch.ergon.dope.resolvable.expression.type.ArrayAccess
import ch.ergon.dope.resolvable.expression.type.ArrayPrimitive
import ch.ergon.dope.resolvable.expression.type.BooleanPrimitive
import ch.ergon.dope.resolvable.expression.type.CaseExpression
import ch.ergon.dope.resolvable.expression.type.DopeVariable
import ch.ergon.dope.resolvable.expression.type.ElseCaseExpression
import ch.ergon.dope.resolvable.expression.type.FALSE
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.expression.type.MISSING
import ch.ergon.dope.resolvable.expression.type.NULL
import ch.ergon.dope.resolvable.expression.type.NumberPrimitive
import ch.ergon.dope.resolvable.expression.type.ObjectEntry
import ch.ergon.dope.resolvable.expression.type.ObjectPrimitive
import ch.ergon.dope.resolvable.expression.type.Parameter
import ch.ergon.dope.resolvable.expression.type.SelectExpression
import ch.ergon.dope.resolvable.expression.type.StringPrimitive
import ch.ergon.dope.resolvable.expression.type.TRUE
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.arithmetic.NegationExpression
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
import ch.ergon.dope.resolvable.expression.type.function.type.ToNumberExpression
import ch.ergon.dope.resolvable.expression.type.logic.NotExpression
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
import ch.ergon.dope.validtype.ValidType

internal object TypeExpressionResolver {
    fun resolve(resolver: CouchbaseResolver, typeExpression: TypeExpression<*>) =
        when (typeExpression) {
            is NotExpression -> {
                val arg = typeExpression.argument.toDopeQuery(resolver)
                CouchbaseDopeQuery(formatToQueryStringWithSeparator("NOT", separator = " ", arg.queryString), arg.parameters)
            }

            is NegationExpression -> {
                val arg = typeExpression.argument.toDopeQuery(resolver)
                CouchbaseDopeQuery(formatToQueryStringWithSeparator("-", separator = "", arg.queryString), arg.parameters)
            }

            is BetweenExpression<*> -> {
                val left = typeExpression.expression.toDopeQuery(resolver)
                val lower = typeExpression.start.toDopeQuery(resolver)
                val upper = typeExpression.end.toDopeQuery(resolver)
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
                val left = typeExpression.expression.toDopeQuery(resolver)
                val lower = typeExpression.start.toDopeQuery(resolver)
                val upper = typeExpression.end.toDopeQuery(resolver)
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
                val fieldDopeQuery = typeExpression.field.toDopeQuery(resolver)
                CouchbaseDopeQuery(formatToQueryString(fieldDopeQuery.queryString, "IS NULL"), fieldDopeQuery.parameters)
            }

            is IsNotNullExpression -> {
                val fieldDopeQuery = typeExpression.field.toDopeQuery(resolver)
                CouchbaseDopeQuery(formatToQueryString(fieldDopeQuery.queryString, "IS NOT NULL"), fieldDopeQuery.parameters)
            }

            is IsMissingExpression -> {
                val fieldDopeQuery = typeExpression.field.toDopeQuery(resolver)
                CouchbaseDopeQuery(formatToQueryString(fieldDopeQuery.queryString, "IS MISSING"), fieldDopeQuery.parameters)
            }

            is IsNotMissingExpression -> {
                val fieldDopeQuery = typeExpression.field.toDopeQuery(resolver)
                CouchbaseDopeQuery(formatToQueryString(fieldDopeQuery.queryString, "IS NOT MISSING"), fieldDopeQuery.parameters)
            }

            is IsValuedExpression -> {
                val fieldDopeQuery = typeExpression.field.toDopeQuery(resolver)
                CouchbaseDopeQuery(formatToQueryString(fieldDopeQuery.queryString, "IS VALUED"), fieldDopeQuery.parameters)
            }

            is IsNotValuedExpression -> {
                val fieldDopeQuery = typeExpression.field.toDopeQuery(resolver)
                CouchbaseDopeQuery(formatToQueryString(fieldDopeQuery.queryString, "IS NOT VALUED"), fieldDopeQuery.parameters)
            }

            is ExistsExpression<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                CouchbaseDopeQuery(queryString = "EXISTS ${arrayDopeQuery.queryString}", parameters = arrayDopeQuery.parameters)
            }

            is SatisfiesExpression<*> -> {
                val array = typeExpression.arrayExpression.toDopeQuery(resolver)
                val iteratorName = typeExpression.iteratorName ?: resolver.manager.iteratorManager.getIteratorName()

                @Suppress("UNCHECKED_CAST")
                val predicateFunc =
                    typeExpression.predicate as (Iterator<ValidType>) -> TypeExpression<BooleanType>
                val predicate = predicateFunc(Iterator(iteratorName)).toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = "${typeExpression.satisfiesType} `$iteratorName` " +
                        "IN ${array.queryString} SATISFIES ${predicate.queryString} END",
                    parameters = array.parameters.merge(predicate.parameters),
                )
            }

            is Iterator<*> -> CouchbaseDopeQuery("`${typeExpression.variable}`")

            is SelectExpression<*> -> {
                val inner = typeExpression.selectClause.toDopeQuery(resolver)
                CouchbaseDopeQuery("(${inner.queryString})", inner.parameters)
            }

            is ArrayAccess<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                val indexDopeQuery = typeExpression.index.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    "${arrayDopeQuery.queryString}[${indexDopeQuery.queryString}]",
                    arrayDopeQuery.parameters.merge(indexDopeQuery.parameters),
                )
            }

            is ObjectEntry<*> -> {
                val objectDopeQuery = typeExpression.objectExpression.toDopeQuery(resolver)
                CouchbaseDopeQuery("${objectDopeQuery.queryString}.`${typeExpression.key}`", objectDopeQuery.parameters)
            }

            is MetaExpression -> {
                val bucket = typeExpression.bucket
                if (bucket == null) {
                    CouchbaseDopeQuery(
                        queryString = "META()",
                    )
                } else {
                    val bucketDopeQuery = bucket.toDopeQuery(resolver)
                    CouchbaseDopeQuery(
                        queryString = typeExpression.toFunctionQueryString(
                            symbol = "META",
                            bucketDopeQuery.queryString,
                        ),
                        parameters = bucketDopeQuery.parameters,
                    )
                }
            }

            is MetaField<*> -> {
                val meta = typeExpression.metaExpression.toDopeQuery(resolver)
                CouchbaseDopeQuery("${meta.queryString}.`${typeExpression.name}`", meta.parameters)
            }

            is Parameter<*> -> {
                when (val name = typeExpression.parameterName) {
                    null -> {
                        val placeholder = "$" + resolver.manager.parameterManager.count
                        CouchbaseDopeQuery(
                            queryString = placeholder,
                            parameters = DopeParameters(positionalParameters = listOf(typeExpression.value)),
                        )
                    }

                    else -> CouchbaseDopeQuery(
                        queryString = "$$name",
                        parameters = DopeParameters(namedParameters = mapOf(name to typeExpression.value)),
                    )
                }
            }

            NULL -> CouchbaseDopeQuery("NULL")
            MISSING -> CouchbaseDopeQuery("MISSING")
            TRUE -> CouchbaseDopeQuery("TRUE")
            FALSE -> CouchbaseDopeQuery("FALSE")

            is NumberPrimitive -> CouchbaseDopeQuery("${typeExpression.value}")
            is StringPrimitive -> CouchbaseDopeQuery("\"${typeExpression.value}\"")
            is BooleanPrimitive -> {
                when (typeExpression.value) {
                    true -> CouchbaseDopeQuery("TRUE")
                    false -> CouchbaseDopeQuery("FALSE")
                }
            }

            is ArrayPrimitive<*> -> {
                val items = typeExpression.collection.map { it.toDopeQuery(resolver) }
                CouchbaseDopeQuery(
                    queryString = formatListToQueryStringWithBrackets(items, prefix = "[", postfix = "]"),
                    parameters = items.map { it.parameters }.merge(),
                )
            }

            is DateUnitType -> CouchbaseDopeQuery("\"${typeExpression.name}\"")
            is DateComponentType -> CouchbaseDopeQuery("\"${typeExpression.name}\"")

            is ObjectPrimitive -> {
                val entries = typeExpression.entries.map { it.toDopeQuery(resolver) }
                CouchbaseDopeQuery(
                    queryString = "{${entries.joinToString(", ") { it.queryString }}}",
                    parameters = entries.map { it.parameters }.merge(),
                )
            }

            is CaseExpression<*, *> -> {
                val case = typeExpression.case.toDopeQuery(resolver)
                val pairs = listOf(typeExpression.firstSearchResult) + typeExpression.additionalSearchResults
                val expressionDopeQueries = pairs.map { it.searchExpression.toDopeQuery(resolver) to it.resultExpression.toDopeQuery(resolver) }
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
                val case = typeExpression.case.toDopeQuery(resolver)
                val pairs = listOf(typeExpression.firstSearchResult) + typeExpression.additionalSearchResults
                val expressionDopeQueries = pairs.map { it.searchExpression.toDopeQuery(resolver) to it.resultExpression.toDopeQuery(resolver) }
                val elseDopeQuery = typeExpression.elseCase.toDopeQuery(resolver)
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
                    queryString = formatPathToQueryString(typeExpression.name, typeExpression.path),
                )
            }

            is FunctionExpression<*> -> {
                val argumentsDopeQuery = typeExpression.expressions.mapNotNull { it?.toDopeQuery(resolver) }
                CouchbaseDopeQuery(
                    queryString = typeExpression.toFunctionQueryString(
                        typeExpression.symbol,
                        *argumentsDopeQuery.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = argumentsDopeQuery.map { it.parameters }.merge(),
                )
            }

            is NumberFunctionExpression -> {
                val v = typeExpression.value?.toDopeQuery(resolver)
                val a = typeExpression.additionalValue?.toDopeQuery(resolver)
                val queryString = typeExpression.toFunctionQueryString(typeExpression.symbol, v?.queryString, a?.queryString)
                CouchbaseDopeQuery(
                    queryString = queryString,
                    parameters = v?.parameters.orEmpty().merge(a?.parameters),
                )
            }

            is ArrayFunctionExpression<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                val argumentsDopeQuery = typeExpression.arguments.map { it.toDopeQuery(resolver) }
                CouchbaseDopeQuery(
                    queryString = typeExpression.toFunctionQueryString(
                        typeExpression.symbol,
                        arrayDopeQuery.queryString,
                        *argumentsDopeQuery.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = arrayDopeQuery.parameters.merge(*argumentsDopeQuery.map { it.parameters }.toTypedArray()),
                )
            }

            is ArrayRepeatExpression<*> -> {
                val valueDopeQuery = typeExpression.value.toDopeQuery(resolver)
                val repetitionsDopeQuery = typeExpression.repetitions.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = typeExpression.toFunctionQueryString(
                        "ARRAY_REPEAT",
                        valueDopeQuery.queryString,
                        repetitionsDopeQuery.queryString,
                    ),
                    parameters = valueDopeQuery.parameters.merge(repetitionsDopeQuery.parameters),
                )
            }

            is ArrayContainsExpression<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                val valueDopeQuery = typeExpression.value.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = typeExpression.toFunctionQueryString(
                        "ARRAY_CONTAINS",
                        arrayDopeQuery.queryString,
                        valueDopeQuery.queryString,
                    ),
                    parameters = arrayDopeQuery.parameters.merge(valueDopeQuery.parameters),
                )
            }

            is TokensExpression -> {
                val optionsDopeQuery = typeExpression.opt.toDopeQuery(resolver)
                val functionQueryString = typeExpression.toFunctionQueryString(
                    "TOKENS",
                    formatStringListToQueryStringWithBrackets(typeExpression.inStr, prefix = "[\"", postfix = "\"]"),
                    optionsDopeQuery.queryString,
                )
                CouchbaseDopeQuery(functionQueryString, optionsDopeQuery.parameters)
            }

            is MaskExpression -> {
                val inputStringDopeQuery = typeExpression.inStr.toDopeQuery(resolver)
                val optionsString = "{" + typeExpression.options.map { "\"${it.key}\": \"${it.value}\"" }.joinToString(", ") + "}"
                val functionQueryString =
                    typeExpression.toFunctionQueryString("MASK", inputStringDopeQuery.queryString, optionsString)
                CouchbaseDopeQuery(functionQueryString, inputStringDopeQuery.parameters)
            }

            is ISearchFunctionExpression -> {
                val field = typeExpression.field?.toDopeQuery(resolver)
                val bucket = typeExpression.bucket?.toDopeQuery(resolver)
                val stringSearch =
                    typeExpression.stringSearchExpression?.let { StringPrimitive(it).toDopeQuery(resolver) }
                val objectSearch = typeExpression.objectSearchExpression?.toDopeType()?.toDopeQuery(resolver)
                val options = typeExpression.options?.toDopeType()?.toDopeQuery(resolver)
                val queryString = typeExpression.toFunctionQueryString(
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
                val queryString = typeExpression.toFunctionQueryString(
                    typeExpression.searchFunctionType.type,
                    typeExpression.outName?.let { "`$it`" },
                )
                CouchbaseDopeQuery(queryString)
            }

            is ToNumberExpression<*> -> {
                val expressionDopeQuery = typeExpression.expression.toDopeQuery(resolver)
                val filter = typeExpression.filterChars?.toDopeQuery(resolver)
                val queryString =
                    typeExpression.toFunctionQueryString("TONUMBER", expressionDopeQuery.queryString, filter?.queryString)
                CouchbaseDopeQuery(queryString, expressionDopeQuery.parameters.merge(filter?.parameters))
            }

            is ArrayAverageExpression<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    typeExpression.toFunctionQueryString("ARRAY_AVG", arrayDopeQuery.queryString),
                    arrayDopeQuery.parameters,
                )
            }

            is ArrayMinExpression<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    typeExpression.toFunctionQueryString("ARRAY_MIN", arrayDopeQuery.queryString),
                    arrayDopeQuery.parameters,
                )
            }

            is ArrayMaxExpression<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    typeExpression.toFunctionQueryString("ARRAY_MAX", arrayDopeQuery.queryString),
                    arrayDopeQuery.parameters,
                )
            }

            is ArraySumExpression<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    typeExpression.toFunctionQueryString("ARRAY_SUM", arrayDopeQuery.queryString),
                    arrayDopeQuery.parameters,
                )
            }

            is ArrayCountExpression<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    typeExpression.toFunctionQueryString("ARRAY_COUNT", arrayDopeQuery.queryString),
                    arrayDopeQuery.parameters,
                )
            }

            is ArrayLengthExpression<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    typeExpression.toFunctionQueryString("ARRAY_LENGTH", arrayDopeQuery.queryString),
                    arrayDopeQuery.parameters,
                )
            }

            is ArrayPositionExpression<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                val valueDopeQuery = typeExpression.value.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    typeExpression.toFunctionQueryString(
                        "ARRAY_POSITION",
                        arrayDopeQuery.queryString,
                        valueDopeQuery.queryString,
                    ),
                    arrayDopeQuery.parameters.merge(valueDopeQuery.parameters),
                )
            }

            is ArrayBinarySearchExpression<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                val valueDopeQuery = typeExpression.value.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    typeExpression.toFunctionQueryString(
                        "ARRAY_BINARY_SEARCH",
                        arrayDopeQuery.queryString,
                        valueDopeQuery.queryString,
                    ),
                    arrayDopeQuery.parameters.merge(valueDopeQuery.parameters),
                )
            }

            is ArrayIfNullExpression<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    typeExpression.toFunctionQueryString("ARRAY_IFNULL", arrayDopeQuery.queryString),
                    arrayDopeQuery.parameters,
                )
            }

            is ArrayPrependExpression<*> -> {
                val arrayDopeQuery = typeExpression.array.toDopeQuery(resolver)
                val valueDopeQuery = typeExpression.value.toDopeQuery(resolver)
                val additionalValueDopeQueries = typeExpression.additionalValues.map { it.toDopeQuery(resolver) }
                val functionQueryString = typeExpression.toFunctionQueryString(
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
                val startDopeQuery = typeExpression.start.toDopeQuery(resolver)
                val endDopeQuery = typeExpression.end.toDopeQuery(resolver)
                val step = typeExpression.step?.toDopeQuery(resolver)
                val functionQueryString = typeExpression.toFunctionQueryString(
                    "ARRAY_RANGE",
                    startDopeQuery.queryString,
                    endDopeQuery.queryString,
                    step?.queryString,
                )
                CouchbaseDopeQuery(functionQueryString, startDopeQuery.parameters.merge(endDopeQuery.parameters, step?.parameters))
            }

            is UnpackExpression -> {
                val objectDopeQuery = typeExpression.objectArray.toDopeQuery(resolver)
                CouchbaseDopeQuery("${objectDopeQuery.queryString}[*]", objectDopeQuery.parameters)
            }

            is Nvl2Expression<*> -> {
                val initialExpressionDopeQuery = typeExpression.initialExpression.toDopeQuery(resolver)
                val valueIfExistsDopeQuery = typeExpression.valueIfExists.toDopeQuery(resolver)
                val valueIfNotExistsDopeQuery = typeExpression.valueIfNotExists.toDopeQuery(resolver)
                val functionQueryString = typeExpression.toFunctionQueryString(
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
                val decodeExpressionDopeQuery = typeExpression.decodeExpression.toDopeQuery(resolver)
                fun pair(searchResult: SearchResult<*, *>): CouchbaseDopeQuery {
                    val searchExpressionDopeQuery =
                        searchResult.searchExpression.toDopeQuery(resolver)
                    val resultExpressionDopeQuery = searchResult.resultExpression.toDopeQuery(resolver)
                    return CouchbaseDopeQuery(
                        "${searchExpressionDopeQuery.queryString}, ${resultExpressionDopeQuery.queryString}",
                        searchExpressionDopeQuery.parameters.merge(resultExpressionDopeQuery.parameters),
                    )
                }

                val firstPairDopeQuery = pair(typeExpression.searchResult)
                val additionalPairDopeQueries = typeExpression.searchResults.map { pair(it) }
                val defaultDopeQuery = typeExpression.default?.toDopeQuery(resolver)
                val functionQueryString = typeExpression.toFunctionQueryString(
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

            is DopeVariable<*> -> CouchbaseDopeQuery("`${typeExpression.name}`")

            else -> throw UnsupportedOperationException("Not supported: $typeExpression")
        }
}
