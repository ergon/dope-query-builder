package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class IsStringExpression<T : ValidType>(
    private val expression: TypeExpression<T>,
) : TypeExpression<BooleanType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("ISSTRING", expressionDopeQuery),
            parameters = expressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> TypeExpression<T>.isString() = IsStringExpression(this)
