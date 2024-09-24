package ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ValidType

class DecodeExpression<T : ValidType, U : ValidType>(
    private val decodeExpression: UnaliasedExpression<T>,
    private val searchResult: SearchResult<T, out U>,
    private vararg val searchResults: SearchResult<T, out U>,
    private val default: UnaliasedExpression<out U>? = null,
) : TypeExpression<U>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val decodeExpressionDopeQuery = decodeExpression.toDopeQuery(manager)
        val searchResultDopeQuery = getSearchResultDopeQuery(searchResult, manager)
        val searchResultsDopeQuery = searchResults.map { getSearchResultDopeQuery(it, manager) }.toTypedArray()
        val defaultDopeQuery = default?.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(
                "DECODE",
                decodeExpressionDopeQuery,
                searchResultDopeQuery,
                *searchResultsDopeQuery,
                defaultDopeQuery,
            ),
            parameters = decodeExpressionDopeQuery.parameters +
                searchResultsDopeQuery.fold(searchResultDopeQuery.parameters) {
                        expressionParameters, expression ->
                    expressionParameters + expression.parameters
                } + defaultDopeQuery?.parameters.orEmpty(),
        )
    }

    private fun getSearchResultDopeQuery(searchResult: SearchResult<T, out U>, manager: DopeQueryManager): DopeQuery {
        val searchDopeQuery = searchResult.searchExpression.toDopeQuery(manager)
        val resultDopeQuery = searchResult.resultExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${searchDopeQuery.queryString}, ${resultDopeQuery.queryString}",
            parameters = searchDopeQuery.parameters + resultDopeQuery.parameters,
        )
    }
}

@JvmName("decodeWithGeneric")
fun <T : ValidType, U : ValidType> decode(
    decodeExpression: UnaliasedExpression<T>,
    searchResult: SearchResult<T, U>,
    vararg searchResults: SearchResult<T, U>,
) = DecodeExpression(decodeExpression, searchResult, *searchResults)

fun <T : ValidType, U : ValidType> decode(
    decodeExpression: UnaliasedExpression<T>,
    searchResult: SearchResult<T, U>,
    vararg searchResults: SearchResult<T, U>,
    default: UnaliasedExpression<U>,
) = DecodeExpression(decodeExpression, searchResult, *searchResults, default = default)

@JvmName("decodeWithoutGeneric")
fun <T : ValidType> decode(
    decodeExpression: UnaliasedExpression<T>,
    searchResult: SearchResult<T, out ValidType>,
    vararg searchResults: SearchResult<T, out ValidType>,
    default: UnaliasedExpression<out ValidType>? = null,
) = DecodeExpression(decodeExpression, searchResult, *searchResults, default = default)
