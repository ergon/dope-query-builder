package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ValidType

sealed class ConditionalExpression<T : ValidType>(
    private val symbol: String,
    private val firstExpression: UnaliasedExpression<T>,
    private val secondExpression: UnaliasedExpression<T>,
    private vararg val additionalExpressions: UnaliasedExpression<T>,
) : TypeExpression<T>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val firstExpressionDopeQuery = firstExpression.toDopeQuery(manager)
        val secondExpressionDopeQuery = secondExpression.toDopeQuery(manager)
        val additionalExpressionsDopeQuery = additionalExpressions.map { it.toDopeQuery(manager) }
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
            manager = manager,
        )
    }
}
