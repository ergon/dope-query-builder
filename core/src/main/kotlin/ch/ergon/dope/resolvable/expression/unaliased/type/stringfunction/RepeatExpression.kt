package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class RepeatExpression(
    private val inStr: TypeExpression<StringType>,
    private val n: TypeExpression<NumberType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(symbol = "REPEAT", inStr, n)
}

fun repeat(inStr: TypeExpression<StringType>, n: TypeExpression<NumberType>) = RepeatExpression(inStr, n)

fun repeat(inStr: TypeExpression<StringType>, n: Number) = repeat(inStr, n.toNumberType())

fun repeat(inStr: String, n: TypeExpression<NumberType>) = repeat(inStr.toStringType(), n)

fun repeat(inStr: String, n: Number): RepeatExpression = repeat(inStr.toStringType(), n.toNumberType())
