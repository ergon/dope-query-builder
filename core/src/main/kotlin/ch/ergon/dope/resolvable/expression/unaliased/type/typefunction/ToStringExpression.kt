package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ToStringExpression<T : ValidType>(
    private val expression: TypeExpression<T>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("TOSTRING", expressionDopeQuery),
            parameters = expressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> toString(expression: TypeExpression<T>) = ToStringExpression(expression)
