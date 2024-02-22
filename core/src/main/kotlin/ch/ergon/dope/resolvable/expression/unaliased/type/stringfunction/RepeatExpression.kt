package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class RepeatExpression(
    private val inStr: TypeExpression<StringType>,
    private val n: Int,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(symbol = "REPEAT", inStr, n.toNumberType())
}

fun repeat(inStr: TypeExpression<StringType>, n: Int) = RepeatExpression(inStr, n)

fun repeat(inStr: String, n: Int): RepeatExpression = repeat(inStr.toStringType(), n)
