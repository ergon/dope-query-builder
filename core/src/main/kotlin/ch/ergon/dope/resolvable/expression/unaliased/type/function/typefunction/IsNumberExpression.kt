package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class IsNumberExpression<T : ValidType>(
    private val expression: TypeExpression<T>,
) : TypeExpression<BooleanType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString("ISNUMBER", expressionDopeQuery),
            parameters = expressionDopeQuery.parameters,
            positionalParameters = expressionDopeQuery.positionalParameters,
        )
    }
}

fun <T : ValidType> TypeExpression<T>.isNumber() = IsNumberExpression(this)
