package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
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

fun <T : ValidType> TypeExpression<T>.toNumber() = ToNumberExpression(this)

fun String.toNumber() = ToNumberExpression(this.toDopeType())

fun Boolean.toNumber() = ToNumberExpression(this.toDopeType())

fun TypeExpression<StringType>.toNumber(stringExpression: TypeExpression<StringType>) =
    ToNumberExpression(this, stringExpression)

fun TypeExpression<StringType>.toNumber(stringExpression: String) =
    ToNumberExpression(this, stringExpression.toDopeType())

fun String.toNumber(stringExpression: TypeExpression<StringType>) =
    ToNumberExpression(this.toDopeType(), stringExpression)

fun String.toNumber(stringExpression: String) =
    ToNumberExpression(this.toDopeType(), stringExpression.toDopeType())
