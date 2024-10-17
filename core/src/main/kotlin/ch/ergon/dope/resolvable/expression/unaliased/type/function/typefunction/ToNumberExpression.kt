package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ToNumberExpression<T : ValidType>(
    private val expression: TypeExpression<T>,
    private val filterChars: TypeExpression<StringType>? = null,
) : TypeExpression<NumberType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery(manager)
        val filterCharsDopeQuery = filterChars?.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString("TONUMBER", expressionDopeQuery, filterCharsDopeQuery),
            parameters = expressionDopeQuery.parameters + filterCharsDopeQuery?.parameters.orEmpty(),
        )
    }
}

fun <T : ValidType> TypeExpression<T>.toNumber() = ToNumberExpression(this)

fun String.toNumber() = toDopeType().toNumber()

fun Boolean.toNumber() = toDopeType().toNumber()

fun TypeExpression<StringType>.toNumber(filterChars: TypeExpression<StringType>) =
    ToNumberExpression(this, filterChars)

fun TypeExpression<StringType>.toNumber(filterChars: String) = toNumber(filterChars.toDopeType())

fun String.toNumber(filterChars: TypeExpression<StringType>) = toDopeType().toNumber(filterChars)

fun String.toNumber(filterChars: String) = toDopeType().toNumber(filterChars.toDopeType())
