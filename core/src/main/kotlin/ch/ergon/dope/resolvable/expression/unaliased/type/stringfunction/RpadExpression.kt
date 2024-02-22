package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class RpadExpression(
    private val inStr: TypeExpression<StringType>,
    private val size: Int,
    private val extra: TypeExpression<StringType> = " ".toStringType(),
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(symbol = "RPAD", inStr, size.toNumberType(), extra)
}

fun rpad(inStr: TypeExpression<StringType>, size: Int, extra: TypeExpression<StringType> = "".toStringType()) =
    RpadExpression(inStr, size, extra)

fun rpad(inStr: String, size: Int, extra: String = ""): RpadExpression = rpad(inStr.toStringType(), size, extra.toStringType())

fun rpad(inStr: String, size: Int): RpadExpression = rpad(inStr.toStringType(), size)

fun rpad(inStr: TypeExpression<StringType>, size: Int): RpadExpression = RpadExpression(inStr, size)

fun rpad(inStr: TypeExpression<StringType>, size: Int, extra: String = ""): RpadExpression = rpad(inStr, size, extra.toStringType())

fun rpad(inStr: String, size: Int, extra: TypeExpression<StringType> = "".toStringType()): RpadExpression = rpad(
    inStr.toStringType(),
    size,
    extra,
)
