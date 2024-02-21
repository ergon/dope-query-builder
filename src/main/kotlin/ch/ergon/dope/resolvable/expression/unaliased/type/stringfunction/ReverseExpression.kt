package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class ReverseExpression(
    private val inStr: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(symbol = "REVERSE", inStr)
}

fun reverse(inStr: TypeExpression<StringType>) = ReverseExpression(inStr)

fun reverse(inStr: String): ReverseExpression = reverse(inStr.toStringType())
