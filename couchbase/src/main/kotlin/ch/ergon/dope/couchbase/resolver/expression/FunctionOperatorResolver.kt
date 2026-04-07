package ch.ergon.dope.couchbase.resolver.expression

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolvable.expression.type.MetaExpression
import ch.ergon.dope.couchbase.resolver.AbstractCouchbaseResolver
import ch.ergon.dope.couchbase.util.formatFunctionQueryString
import ch.ergon.dope.merge
import ch.ergon.dope.orEmpty
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.DecodeExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.Nvl2Expression
import ch.ergon.dope.resolvable.expression.type.function.conditional.SearchResult
import ch.ergon.dope.resolvable.expression.type.function.search.ISearchFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.search.SearchDependencyFunctionExpression
import ch.ergon.dope.resolvable.expression.type.function.search.SearchFunctionType
import ch.ergon.dope.resolvable.expression.type.function.string.MaskExpression
import ch.ergon.dope.resolvable.expression.type.function.token.ContainsTokenExpression
import ch.ergon.dope.resolvable.expression.type.function.token.ContainsTokenLikeExpression
import ch.ergon.dope.resolvable.expression.type.function.token.ContainsTokenOptions
import ch.ergon.dope.resolvable.expression.type.function.token.ContainsTokenRegexpExpression
import ch.ergon.dope.resolvable.expression.type.function.token.TokensExpression
import ch.ergon.dope.resolvable.expression.type.function.type.ToNumberExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

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
                    queryString = formatFunctionQueryString(
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
                queryString = formatFunctionQueryString(
                    typeExpression.symbol,
                    *argumentsDopeQuery.map { it.queryString }.toTypedArray(),
                ),
                parameters = argumentsDopeQuery.map { it.parameters }.merge(),
            )
        }

        is TokensExpression -> {
            val inStringDopeQuery = typeExpression.inString.toDopeQuery(this)
            val optionsDopeQuery = typeExpression.tokensOptions?.toDopeQuery(this).takeIf { !it?.queryString.isNullOrEmpty() }
            val functionQueryString = formatFunctionQueryString(
                "TOKENS",
                inStringDopeQuery.queryString,
                optionsDopeQuery?.queryString,
            )
            CouchbaseDopeQuery(functionQueryString, inStringDopeQuery.parameters.merge(optionsDopeQuery?.parameters))
        }

        is ContainsTokenExpression -> resolve(
            "CONTAINS_TOKEN",
            typeExpression.inputExpression,
            typeExpression.tokenExpression,
            typeExpression.tokenOptions,
        )

        is ContainsTokenLikeExpression -> resolve(
            "CONTAINS_TOKEN_LIKE",
            typeExpression.inputObject,
            typeExpression.likeExpression,
            typeExpression.tokenOptions,
        )

        is ContainsTokenRegexpExpression -> resolve(
            "CONTAINS_TOKEN_REGEXP",
            typeExpression.inputObject,
            typeExpression.regexExpression,
            typeExpression.tokenOptions,
        )

        is MaskExpression -> {
            val inputStringDopeQuery = typeExpression.inStr.toDopeQuery(this)
            val optionsString =
                "{" + typeExpression.options.map { "\"${it.key}\": \"${it.value}\"" }.joinToString(", ") + "}"
            val functionQueryString =
                formatFunctionQueryString("MASK", inputStringDopeQuery.queryString, optionsString)
            CouchbaseDopeQuery(functionQueryString, inputStringDopeQuery.parameters)
        }

        is ISearchFunctionExpression -> {
            val field = typeExpression.field?.toDopeQuery(this)
            val bucket = typeExpression.bucket?.toDopeQuery(this)
            val stringSearchExpression = typeExpression.stringSearchExpression?.toDopeType()?.toDopeQuery(this)
            val objectSearch = typeExpression.objectSearchExpression?.toDopeType()?.toDopeQuery(this)
            val options = typeExpression.options?.toDopeType()?.toDopeQuery(this)
            val queryString = formatFunctionQueryString(
                SearchFunctionType.SEARCH.name,
                field?.queryString,
                bucket?.queryString,
                stringSearchExpression?.queryString,
                objectSearch?.queryString,
                options?.queryString,
            )
            val params =
                field?.parameters.orEmpty().merge(bucket?.parameters, objectSearch?.parameters, options?.parameters)
            CouchbaseDopeQuery(queryString, params)
        }

        is SearchDependencyFunctionExpression<*> -> {
            val queryString = formatFunctionQueryString(
                typeExpression.searchFunctionType.name,
                typeExpression.outName?.let { "`$it`" },
            )
            CouchbaseDopeQuery(queryString)
        }

        is ToNumberExpression<*> -> {
            val expressionDopeQuery = typeExpression.expression.toDopeQuery(this)
            val filter = typeExpression.filterChars?.toDopeQuery(this)
            val queryString =
                formatFunctionQueryString(
                    "TONUMBER",
                    expressionDopeQuery.queryString,
                    filter?.queryString,
                )
            CouchbaseDopeQuery(queryString, expressionDopeQuery.parameters.merge(filter?.parameters))
        }

        is Nvl2Expression<*> -> {
            val initialExpressionDopeQuery = typeExpression.initialExpression.toDopeQuery(this)
            val valueIfExistsDopeQuery = typeExpression.valueIfExists.toDopeQuery(this)
            val valueIfNotExistsDopeQuery = typeExpression.valueIfNotExists.toDopeQuery(this)
            val functionQueryString = formatFunctionQueryString(
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
            val functionQueryString = formatFunctionQueryString(
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

    private fun resolve(
        symbol: String,
        inputObject: TypeExpression<out ValidType>,
        tokenExpression: TypeExpression<StringType>,
        options: ContainsTokenOptions?,
    ): CouchbaseDopeQuery {
        val inputObjectDopeQuery = inputObject.toDopeQuery(this)
        val tokenExpressionDopeQuery = tokenExpression.toDopeQuery(this)
        val optionsDopeQuery = options?.toDopeQuery(this).takeIf { !it?.queryString.isNullOrEmpty() }
        val functionQueryString = formatFunctionQueryString(
            symbol,
            inputObjectDopeQuery.queryString,
            tokenExpressionDopeQuery.queryString,
            optionsDopeQuery?.queryString,
        )
        return CouchbaseDopeQuery(
            functionQueryString,
            inputObjectDopeQuery.parameters.merge(tokenExpressionDopeQuery.parameters, optionsDopeQuery?.parameters),
        )
    }
}
