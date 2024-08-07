package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ToNumberExpression<T : ValidType>(
    private val expression: TypeExpression<T>,
    private val stringExpression: TypeExpression<StringType>? = null,
) : TypeExpression<NumberType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery()
        val stringExpressionDopeQuery = stringExpression?.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("TONUMBER", expressionDopeQuery, stringExpressionDopeQuery),
            parameters = expressionDopeQuery.parameters + stringExpressionDopeQuery?.parameters.orEmpty(),
        )
    }
}

fun <T : ValidType> toNumber(expression: TypeExpression<T>) = ToNumberExpression(expression)

fun toNumber(
    expression: TypeExpression<StringType>,
    stringExpression: TypeExpression<StringType>,
) = ToNumberExpression(expression, stringExpression)
