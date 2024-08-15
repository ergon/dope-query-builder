package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ValidType

class DecodeExpression<T : ValidType, U : ValidType>(
    private val expression: UnaliasedExpression<T>,
    private val searchResultExpression: SearchResultExpression<T, U>,
    private vararg val searchResultExpressions: SearchResultExpression<T, U>,
    private val default: UnaliasedExpression<U>? = null,
) : TypeExpression<U>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery()
        val searchResultExpressionDopeQuery = searchResultExpression.toDopeQuery()
        val searchResultExpressionsDopeQuery = searchResultExpressions.map { it.toDopeQuery() }.toTypedArray()
        val defaultDopeQuery = default?.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(
                "DECODE",
                expressionDopeQuery,
                searchResultExpressionDopeQuery,
                *searchResultExpressionsDopeQuery,
                defaultDopeQuery,
            ),
            parameters = expressionDopeQuery.parameters +
                searchResultExpressionsDopeQuery.fold(searchResultExpressionDopeQuery.parameters) {
                        expressionParameters, expression ->
                    expressionParameters + expression.parameters
                } + defaultDopeQuery?.parameters.orEmpty(),
        )
    }
}

fun <T : ValidType, U : ValidType> decode(
    expression: UnaliasedExpression<T>,
    searchResultExpression: SearchResultExpression<T, U>,
    vararg searchResultExpressions: SearchResultExpression<T, U>,
    default: UnaliasedExpression<U>? = null,
) = DecodeExpression(expression, searchResultExpression, *searchResultExpressions, default = default)
