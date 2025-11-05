package ch.ergon.dope.couchbase

import ch.ergon.dope.couchbase.resolvable.expression.type.MetaExpression
import ch.ergon.dope.couchbase.util.formatStringListToQueryStringWithBrackets
import ch.ergon.dope.merge
import ch.ergon.dope.orEmpty
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.StringPrimitive
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
import ch.ergon.dope.resolvable.expression.type.function.conditional.DecodeExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.Nvl2Expression
import ch.ergon.dope.resolvable.expression.type.function.conditional.SearchResult
import ch.ergon.dope.resolvable.expression.type.function.numeric.NumberFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.search.ISearchFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.search.SearchDependencyFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.search.SearchFunctionType
import ch.ergon.dope.resolvable.expression.type.function.string.MaskExpression
import ch.ergon.dope.resolvable.expression.type.function.string.TokensExpression
import ch.ergon.dope.resolvable.expression.type.function.type.ToNumberExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType

interface FunctionOperatorResolver : AbstractCouchbaseResolver {
    fun resolve(typeExpression: FunctionOperator<*>): CouchbaseDopeQuery = when (typeExpression) {
        is MetaExpression -> {
            val bucket = typeExpression.bucket
            if (bucket == null) {
                CouchbaseDopeQuery(
                    queryString = "META()",
                )
            } else {
                val bucketDopeQuery = bucket.toDopeQuery(this)
                CouchbaseDopeQuery(
                    queryString = typeExpression.toFunctionQueryString(
                        symbol = "META",
                        bucketDopeQuery.queryString,
                    ),
                    parameters = bucketDopeQuery.parameters,
                )
            }
        }

        is FunctionExpression<*> -> {
            val argumentsDopeQuery = typeExpression.expressions.mapNotNull { it?.toDopeQuery(this) }
            CouchbaseDopeQuery(
                queryString = typeExpression.toFunctionQueryString(
                    typeExpression.symbol,
                    *argumentsDopeQuery.map { it.queryString }.toTypedArray(),
                ),
                parameters = argumentsDopeQuery.map { it.parameters }.merge(),
            )
        }

        is NumberFunctionExpression -> {
            val v = typeExpression.value?.toDopeQuery(this)
            val a = typeExpression.additionalValue?.toDopeQuery(this)
            val queryString =
                typeExpression.toFunctionQueryString(typeExpression.symbol, v?.queryString, a?.queryString)
            CouchbaseDopeQuery(
                queryString = queryString,
                parameters = v?.parameters.orEmpty().merge(a?.parameters),
            )
        }

        is ArrayFunctionExpression<*> -> {
            val arrayDopeQuery = typeExpression.array.toDopeQuery(this)
            val argumentsDopeQuery = typeExpression.arguments.map { it.toDopeQuery(this) }
            CouchbaseDopeQuery(
                queryString = typeExpression.toFunctionQueryString(
                    typeExpression.symbol,
                    arrayDopeQuery.queryString,
                    *argumentsDopeQuery.map { it.queryString }.toTypedArray(),
                ),
                parameters = arrayDopeQuery.parameters.merge(
                    *argumentsDopeQuery.map { it.parameters }
                        .toTypedArray(),
                ),
            )
        }

        is ArrayRepeatExpression<*> -> {
            val valueDopeQuery = typeExpression.value.toDopeQuery(this)
            val repetitionsDopeQuery = typeExpression.repetitions.toDopeQuery(this)
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
            val arrayDopeQuery = typeExpression.array.toDopeQuery(this)
            val valueDopeQuery = typeExpression.value.toDopeQuery(this)
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
            val optionsDopeQuery = typeExpression.opt.toDopeQuery(this)
            val functionQueryString = typeExpression.toFunctionQueryString(
                "TOKENS",
                formatStringListToQueryStringWithBrackets(typeExpression.inStr, prefix = "[\"", postfix = "\"]"),
                optionsDopeQuery.queryString,
            )
            CouchbaseDopeQuery(functionQueryString, optionsDopeQuery.parameters)
        }

        is MaskExpression -> {
            val inputStringDopeQuery = typeExpression.inStr.toDopeQuery(this)
            val optionsString =
                "{" + typeExpression.options.map { "\"${it.key}\": \"${it.value}\"" }.joinToString(", ") + "}"
            val functionQueryString =
                typeExpression.toFunctionQueryString("MASK", inputStringDopeQuery.queryString, optionsString)
            CouchbaseDopeQuery(functionQueryString, inputStringDopeQuery.parameters)
        }

        is ISearchFunctionExpression -> {
            val field = typeExpression.field?.toDopeQuery(this)
            val bucket = typeExpression.bucket?.toDopeQuery(this)
            val stringSearch =
                typeExpression.stringSearchExpression?.let { StringPrimitive(it).toDopeQuery(this) }
            val objectSearch = typeExpression.objectSearchExpression?.toDopeType()?.toDopeQuery(this)
            val options = typeExpression.options?.toDopeType()?.toDopeQuery(this)
            val queryString = typeExpression.toFunctionQueryString(
                SearchFunctionType.SEARCH.name,
                field?.queryString,
                bucket?.queryString,
                stringSearch?.queryString,
                objectSearch?.queryString,
                options?.queryString,
            )
            val params =
                field?.parameters.orEmpty().merge(bucket?.parameters, objectSearch?.parameters, options?.parameters)
            CouchbaseDopeQuery(queryString, params)
        }

        is SearchDependencyFunctionExpression<*> -> {
            val queryString = typeExpression.toFunctionQueryString(
                typeExpression.searchFunctionType.name,
                typeExpression.outName?.let { "`$it`" },
            )
            CouchbaseDopeQuery(queryString)
        }

        is ToNumberExpression<*> -> {
            val expressionDopeQuery = typeExpression.expression.toDopeQuery(this)
            val filter = typeExpression.filterChars?.toDopeQuery(this)
            val queryString =
                typeExpression.toFunctionQueryString(
                    "TONUMBER",
                    expressionDopeQuery.queryString,
                    filter?.queryString,
                )
            CouchbaseDopeQuery(queryString, expressionDopeQuery.parameters.merge(filter?.parameters))
        }

        is ArrayAverageExpression<*> -> {
            val arrayDopeQuery = typeExpression.array.toDopeQuery(this)
            CouchbaseDopeQuery(
                typeExpression.toFunctionQueryString("ARRAY_AVG", arrayDopeQuery.queryString),
                arrayDopeQuery.parameters,
            )
        }

        is ArrayMinExpression<*> -> {
            val arrayDopeQuery = typeExpression.array.toDopeQuery(this)
            CouchbaseDopeQuery(
                typeExpression.toFunctionQueryString("ARRAY_MIN", arrayDopeQuery.queryString),
                arrayDopeQuery.parameters,
            )
        }

        is ArrayMaxExpression<*> -> {
            val arrayDopeQuery = typeExpression.array.toDopeQuery(this)
            CouchbaseDopeQuery(
                typeExpression.toFunctionQueryString("ARRAY_MAX", arrayDopeQuery.queryString),
                arrayDopeQuery.parameters,
            )
        }

        is ArraySumExpression<*> -> {
            val arrayDopeQuery = typeExpression.array.toDopeQuery(this)
            CouchbaseDopeQuery(
                typeExpression.toFunctionQueryString("ARRAY_SUM", arrayDopeQuery.queryString),
                arrayDopeQuery.parameters,
            )
        }

        is ArrayCountExpression<*> -> {
            val arrayDopeQuery = typeExpression.array.toDopeQuery(this)
            CouchbaseDopeQuery(
                typeExpression.toFunctionQueryString("ARRAY_COUNT", arrayDopeQuery.queryString),
                arrayDopeQuery.parameters,
            )
        }

        is ArrayLengthExpression<*> -> {
            val arrayDopeQuery = typeExpression.array.toDopeQuery(this)
            CouchbaseDopeQuery(
                typeExpression.toFunctionQueryString("ARRAY_LENGTH", arrayDopeQuery.queryString),
                arrayDopeQuery.parameters,
            )
        }

        is ArrayPositionExpression<*> -> {
            val arrayDopeQuery = typeExpression.array.toDopeQuery(this)
            val valueDopeQuery = typeExpression.value.toDopeQuery(this)
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
            val arrayDopeQuery = typeExpression.array.toDopeQuery(this)
            val valueDopeQuery = typeExpression.value.toDopeQuery(this)
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
            val arrayDopeQuery = typeExpression.array.toDopeQuery(this)
            CouchbaseDopeQuery(
                typeExpression.toFunctionQueryString("ARRAY_IFNULL", arrayDopeQuery.queryString),
                arrayDopeQuery.parameters,
            )
        }

        is ArrayPrependExpression<*> -> {
            val arrayDopeQuery = typeExpression.array.toDopeQuery(this)
            val valueDopeQuery = typeExpression.value.toDopeQuery(this)
            val additionalValueDopeQueries = typeExpression.additionalValues.map { it.toDopeQuery(this) }
            val functionQueryString = typeExpression.toFunctionQueryString(
                "ARRAY_PREPEND",
                valueDopeQuery.queryString,
                *additionalValueDopeQueries.map { it.queryString }.toTypedArray(),
                arrayDopeQuery.queryString,
            )
            CouchbaseDopeQuery(
                functionQueryString,
                arrayDopeQuery.parameters.merge(
                    valueDopeQuery.parameters,
                    *additionalValueDopeQueries.map { it.parameters }.toTypedArray(),
                ),
            )
        }

        is ArrayRangeExpression -> {
            val startDopeQuery = typeExpression.start.toDopeQuery(this)
            val endDopeQuery = typeExpression.end.toDopeQuery(this)
            val step = typeExpression.step?.toDopeQuery(this)
            val functionQueryString = typeExpression.toFunctionQueryString(
                "ARRAY_RANGE",
                startDopeQuery.queryString,
                endDopeQuery.queryString,
                step?.queryString,
            )
            CouchbaseDopeQuery(
                functionQueryString,
                startDopeQuery.parameters.merge(endDopeQuery.parameters, step?.parameters),
            )
        }

        is Nvl2Expression<*> -> {
            val initialExpressionDopeQuery = typeExpression.initialExpression.toDopeQuery(this)
            val valueIfExistsDopeQuery = typeExpression.valueIfExists.toDopeQuery(this)
            val valueIfNotExistsDopeQuery = typeExpression.valueIfNotExists.toDopeQuery(this)
            val functionQueryString = typeExpression.toFunctionQueryString(
                "NVL2",
                initialExpressionDopeQuery.queryString,
                valueIfExistsDopeQuery.queryString,
                valueIfNotExistsDopeQuery.queryString,
            )
            CouchbaseDopeQuery(
                functionQueryString,
                initialExpressionDopeQuery.parameters.merge(
                    valueIfExistsDopeQuery.parameters,
                    valueIfNotExistsDopeQuery.parameters,
                ),
            )
        }

        is DecodeExpression<*, *> -> {
            val decodeExpressionDopeQuery = typeExpression.decodeExpression.toDopeQuery(this)
            fun pair(searchResult: SearchResult<*, *>): CouchbaseDopeQuery {
                val searchExpressionDopeQuery =
                    searchResult.searchExpression.toDopeQuery(this)
                val resultExpressionDopeQuery = searchResult.resultExpression.toDopeQuery(this)
                return CouchbaseDopeQuery(
                    "${searchExpressionDopeQuery.queryString}, ${resultExpressionDopeQuery.queryString}",
                    searchExpressionDopeQuery.parameters.merge(resultExpressionDopeQuery.parameters),
                )
            }

            val firstPairDopeQuery = pair(typeExpression.searchResult)
            val additionalPairDopeQueries = typeExpression.searchResults.map { pair(it) }
            val defaultDopeQuery = typeExpression.default?.toDopeQuery(this)
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

        else -> throw UnsupportedOperationException("Not supported: $typeExpression")
    }
}
