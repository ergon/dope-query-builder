package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class LpadExpression(
    private val inStr: TypeExpression<StringType>,
    private val size: Int,
    private val extra: TypeExpression<StringType> = " ".toStringType(),
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String =
        if (extra.toQueryString().isBlank()) {
            toFunctionQueryString(symbol = "LPAD", inStr, size.toNumberType())
        } else {
            toFunctionQueryString(symbol = "LPAD", inStr, size.toNumberType(), extra)
        }
}

fun lpad(inStr: TypeExpression<StringType>, size: Int, extra: TypeExpression<StringType> = "".toStringType()) =
    LpadExpression(inStr, size, extra)

fun lpad(inStr: String, size: Int, extra: String = ""): LpadExpression = lpad(inStr.toStringType(), size, extra.toStringType())

fun lpad(inStr: TypeExpression<StringType>, size: Int, extra: String = "") = lpad(inStr, size, extra.toStringType())

fun lpad(inStr: String, size: Int, extra: TypeExpression<StringType> = "".toStringType()) = lpad(
    inStr.toStringType(),
    size,
    extra,
)

fun lpad(inStr: TypeExpression<StringType>, size: Int) = LpadExpression(inStr, size)

fun lpad(inStr: String, size: Int): LpadExpression = lpad(inStr.toStringType(), size)
