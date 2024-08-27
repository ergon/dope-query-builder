package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class RpadExpression(
    private val inStr: TypeExpression<StringType>,
    private val size: TypeExpression<NumberType>,
    private val char: TypeExpression<StringType>? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery(manager)
        val sizeDopeQuery = size.toDopeQuery(manager)
        val charDopeQuery = char?.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "RPAD", inStrDopeQuery, sizeDopeQuery, charDopeQuery),
            parameters = inStrDopeQuery.parameters + sizeDopeQuery.parameters + charDopeQuery?.parameters.orEmpty(),
        )
    }
}

fun rpad(
    inStr: TypeExpression<StringType>,
    size: TypeExpression<NumberType>,
    char: TypeExpression<StringType>? = null,
) = RpadExpression(inStr, size, char)

fun rpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, char: String) = rpad(inStr, size, char.toDopeType())

fun rpad(inStr: TypeExpression<StringType>, size: Number, char: TypeExpression<StringType>) = rpad(inStr, size.toDopeType(), char)

fun rpad(inStr: TypeExpression<StringType>, size: Number, char: String) = rpad(inStr, size.toDopeType(), char)

fun rpad(inStr: TypeExpression<StringType>, size: Number) = rpad(inStr, size.toDopeType())

fun rpad(inStr: String, size: TypeExpression<NumberType>, char: TypeExpression<StringType>) = rpad(inStr.toDopeType(), size, char)

fun rpad(inStr: String, size: TypeExpression<NumberType>) = rpad(inStr.toDopeType(), size)

fun rpad(inStr: String, size: TypeExpression<NumberType>, prefix: String) = rpad(inStr.toDopeType(), size, prefix.toDopeType())

fun rpad(inStr: String, size: Number, char: TypeExpression<StringType>) = rpad(inStr.toDopeType(), size.toDopeType(), char)

fun rpad(inStr: String, size: Number, char: String) = rpad(inStr.toDopeType(), size.toDopeType(), char.toDopeType())

fun rpad(inStr: String, size: Number) = rpad(inStr.toDopeType(), size.toDopeType())
