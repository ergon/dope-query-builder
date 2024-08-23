package ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ValidType

class DecodeExpression<T : ValidType, U : ValidType>(
    private val decodeExpression: UnaliasedExpression<T>,
    private val searchResult: SearchResult<T, U>,
    private vararg val searchResults: SearchResult<T, U>,
    private val default: UnaliasedExpression<U>? = null,
) : TypeExpression<U>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val decodeExpressionDopeQuery = decodeExpression.toDopeQuery()
        val searchResultDopeQuery = searchResult.toDopeQuery()
        val searchResultsDopeQuery = searchResults.map { it.toDopeQuery() }.toTypedArray()
        val defaultDopeQuery = default?.toDopeQuery()
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
}

fun <T : ValidType, U : ValidType> decode(
    decodeExpression: UnaliasedExpression<T>,
    searchResult: SearchResult<T, U>,
    vararg searchResults: SearchResult<T, U>,
    default: UnaliasedExpression<U>? = null,
) = DecodeExpression(decodeExpression, searchResult, *searchResults, default = default)
