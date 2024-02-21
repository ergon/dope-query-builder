package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class LengthExpression(
    private val inStr: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(symbol = "LENGTH", inStr)
}

fun length(inStr: TypeExpression<StringType>): LengthExpression = LengthExpression(inStr)

fun length(inStr: String): LengthExpression = length(inStr.toStringType())
