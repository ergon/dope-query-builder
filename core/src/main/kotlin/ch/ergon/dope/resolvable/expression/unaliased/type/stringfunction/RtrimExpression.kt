package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class RtrimExpression(
    private val inStr: TypeExpression<StringType>,
    private val extra: String = " ",
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(symbol = "RTRIM", inStr, extra = extra.toStringType())
}

fun rtrim(inStr: TypeExpression<StringType>, extra: String) = RtrimExpression(inStr, extra)

fun rtrim(inStr: String) = rtrim(inStr.toStringType(), " ")

fun rtrim(inStr: String, extra: Char) = rtrim(inStr.toStringType(), extra.toString())

fun rtrim(inStr: String, extra: String) = rtrim(inStr.toStringType(), extra)

fun rtrim(inStr: TypeExpression<StringType>) = rtrim(inStr, " ")

fun rtrim(inStr: TypeExpression<StringType>, extra: Char) = rtrim(inStr, extra.toString())
