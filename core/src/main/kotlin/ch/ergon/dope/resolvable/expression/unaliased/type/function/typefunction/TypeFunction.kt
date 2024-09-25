package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ValidType

sealed class TypeFunction<T : ValidType, ReturnType : ValidType>(private val expression: TypeExpression<T>, val symbol: String) :
    TypeExpression<ReturnType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(symbol, expressionDopeQuery),
            parameters = expressionDopeQuery.parameters,
        )
    }
}
