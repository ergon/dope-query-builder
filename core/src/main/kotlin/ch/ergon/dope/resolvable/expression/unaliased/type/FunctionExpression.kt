package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ValidType

open class FunctionExpression<T : ValidType>(
    private val symbol: String,
    private val firstExpression: UnaliasedExpression<T>,
    private val secondExpression: UnaliasedExpression<T>,
    private vararg val additionalExpressions: UnaliasedExpression<T>,
) : TypeExpression<T>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val firstExpressionDopeQuery = firstExpression.toDopeQuery()
        val secondExpressionDopeQuery = secondExpression.toDopeQuery()
        val additionalExpressionsDopeQuery = additionalExpressions.map { it.toDopeQuery() }
        return DopeQuery(
            queryString = toFunctionQueryString(
                symbol,
                firstExpressionDopeQuery,
                secondExpressionDopeQuery,
                *additionalExpressionsDopeQuery.toTypedArray(),
            ),
            parameters = firstExpressionDopeQuery.parameters + additionalExpressionsDopeQuery.fold(
                secondExpressionDopeQuery.parameters,
            ) { expressionParameters, expression -> expressionParameters + expression.parameters },
        )
    }
}
