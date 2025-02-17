package ch.ergon.dope.resolvable.expression.type.function.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

class DecodeExpression<T : ValidType, U : ValidType>(
    private val decodeExpression: TypeExpression<T>,
    private val searchResult: SearchResult<T, out U>,
    private vararg val searchResults: SearchResult<T, out U>,
    private val default: TypeExpression<out U>? = null,
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
            parameters = decodeExpressionDopeQuery.parameters.merge(
                searchResultDopeQuery.parameters,
                *searchResultsDopeQuery.map { it.parameters }.toTypedArray(),
                defaultDopeQuery?.parameters,
            ),
        )
    }

    private fun getSearchResultDopeQuery(searchResult: SearchResult<T, out U>, manager: DopeQueryManager): DopeQuery {
        val searchDopeQuery = searchResult.searchExpression.toDopeQuery(manager)
        val resultDopeQuery = searchResult.resultExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${searchDopeQuery.queryString}, ${resultDopeQuery.queryString}",
            parameters = searchDopeQuery.parameters.merge(resultDopeQuery.parameters),
        )
    }
}

@JvmName("decodeWithGeneric")
fun <T : ValidType, U : ValidType> decode(
    decodeExpression: TypeExpression<T>,
    searchResult: SearchResult<T, U>,
    vararg searchResults: SearchResult<T, U>,
) = DecodeExpression(decodeExpression, searchResult, *searchResults)

fun <T : ValidType, U : ValidType> decode(
    decodeExpression: TypeExpression<T>,
    searchResult: SearchResult<T, U>,
    vararg searchResults: SearchResult<T, U>,
    default: TypeExpression<U>,
) = DecodeExpression(decodeExpression, searchResult, *searchResults, default = default)

@JvmName("decodeWithoutGeneric")
fun <T : ValidType> decode(
    decodeExpression: TypeExpression<T>,
    searchResult: SearchResult<T, out ValidType>,
    vararg searchResults: SearchResult<T, out ValidType>,
    default: TypeExpression<out ValidType>? = null,
) = DecodeExpression(decodeExpression, searchResult, *searchResults, default = default)
