package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class RpadExpression(
    private val inStr: TypeExpression<StringType>,
    private val size: TypeExpression<NumberType>,
    private val extra: TypeExpression<StringType>? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery()
        val sizeDopeQuery = size.toDopeQuery()
        val extraDopeQuery = extra?.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "RPAD", inStrDopeQuery, sizeDopeQuery, extra = extraDopeQuery),
            parameters = inStrDopeQuery.parameters + sizeDopeQuery.parameters + extraDopeQuery?.parameters.orEmpty(),
        )
    }
}

fun rpad(
    inStr: TypeExpression<StringType>,
    size: TypeExpression<NumberType>,
    extra: TypeExpression<StringType>? = null,
) = RpadExpression(inStr, size, extra)

fun rpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, extra: String) = rpad(inStr, size, extra.toDopeType())

fun rpad(inStr: TypeExpression<StringType>, size: Number, extra: TypeExpression<StringType>) = rpad(inStr, size.toDopeType(), extra)

fun rpad(inStr: TypeExpression<StringType>, size: Number, extra: String) = rpad(inStr, size.toDopeType(), extra)

fun rpad(inStr: TypeExpression<StringType>, size: Number) = rpad(inStr, size.toDopeType())

fun rpad(inStr: String, size: TypeExpression<NumberType>, extra: TypeExpression<StringType>) = rpad(inStr.toDopeType(), size, extra)

fun rpad(inStr: String, size: TypeExpression<NumberType>) = rpad(inStr.toDopeType(), size)

fun rpad(inStr: String, size: TypeExpression<NumberType>, prefix: String) = rpad(inStr.toDopeType(), size, prefix.toDopeType())

fun rpad(inStr: String, size: Number, extra: TypeExpression<StringType>) = rpad(inStr.toDopeType(), size.toDopeType(), extra)

fun rpad(inStr: String, size: Number, extra: String) = rpad(inStr.toDopeType(), size.toDopeType(), extra.toDopeType())

fun rpad(inStr: String, size: Number) = rpad(inStr.toDopeType(), size.toDopeType())
