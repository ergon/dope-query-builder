package ch.ergon.dope.resolvable.expression.single.type.function.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.util.operator.FunctionOperator
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
            parameters = expressionDopeQuery.parameters.merge(filterCharsDopeQuery?.parameters),
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
