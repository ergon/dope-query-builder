package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class RpadExpression(
    private val inStr: TypeExpression<StringType>,
    private val size: TypeExpression<NumberType>,
    private val extra: TypeExpression<StringType>? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toQuery()
        val sizeDopeQuery = size.toQuery()
        val extraDopeQuery = extra?.toQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "RPAD", inStrDopeQuery, sizeDopeQuery, extra = extraDopeQuery),
            parameters = inStrDopeQuery.parameters + sizeDopeQuery.parameters + (extraDopeQuery?.parameters ?: emptyMap()),
        )
    }
}

fun rpad(
    inStr: TypeExpression<StringType>,
    size: TypeExpression<NumberType>,
    extra: TypeExpression<StringType>? = null,
): RpadExpression =
    RpadExpression(inStr, size, extra)

fun rpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, extra: String): RpadExpression =
    rpad(inStr, size, extra.toStringType())

fun rpad(inStr: TypeExpression<StringType>, size: Number, extra: TypeExpression<StringType>): RpadExpression =
    rpad(inStr, size.toNumberType(), extra)

fun rpad(inStr: TypeExpression<StringType>, size: Number, extra: String): RpadExpression = rpad(inStr, size.toNumberType(), extra)

fun rpad(inStr: TypeExpression<StringType>, size: Number): RpadExpression = rpad(inStr, size.toNumberType())

fun rpad(inStr: String, size: TypeExpression<NumberType>, extra: TypeExpression<StringType>): RpadExpression =
    rpad(inStr.toStringType(), size, extra)

fun rpad(inStr: String, size: TypeExpression<NumberType>): RpadExpression = rpad(inStr.toStringType(), size)

fun rpad(inStr: String, size: Number, extra: TypeExpression<StringType>): RpadExpression = rpad(inStr.toStringType(), size.toNumberType(), extra)

fun rpad(inStr: String, size: Number, extra: String): RpadExpression = rpad(inStr.toStringType(), size.toNumberType(), extra.toStringType())

fun rpad(inStr: String, size: Number): RpadExpression = rpad(inStr.toStringType(), size.toNumberType())
