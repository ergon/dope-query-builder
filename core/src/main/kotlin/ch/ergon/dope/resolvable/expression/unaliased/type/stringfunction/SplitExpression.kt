package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class SplitExpression(
    private val inStr: TypeExpression<StringType>,
    private val inSubstring: TypeExpression<StringType> = " ".toStringType(),
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(symbol = "SPLIT", inStr, extra = inSubstring)
}

fun split(inStr: TypeExpression<StringType>, inSubstring: TypeExpression<StringType> = "".toStringType()) = SplitExpression(inStr, inSubstring)

fun split(inStr: String): SplitExpression = split(inStr.toStringType())

fun split(inStr: TypeExpression<StringType>): SplitExpression = SplitExpression(inStr)

fun split(inStr: String, inSubstring: String = ""): SplitExpression = split(inStr.toStringType(), inSubstring.toStringType())

fun split(inStr: TypeExpression<StringType>, inSubstring: String = ""): SplitExpression = split(inStr, inSubstring.toStringType())

fun split(inStr: String, inSubstring: TypeExpression<StringType> = "".toStringType()) = split(
    inStr.toStringType(),
    inSubstring,
)
