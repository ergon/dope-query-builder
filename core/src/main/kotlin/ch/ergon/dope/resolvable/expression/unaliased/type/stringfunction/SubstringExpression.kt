package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class SubstringExpression(
    private val inStr: TypeExpression<StringType>,
    private val startPos: Int,
    private val length: Int,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(symbol = "SUBSTR", inStr, startPos.toNumberType(), length.toNumberType())
}

fun substr(inStr: TypeExpression<StringType>, startPos: Int, length: Int = inStr.toQueryString().length) =
    SubstringExpression(inStr, startPos, length)

fun substr(inStr: String, startPos: Int, length: Int = inStr.length): SubstringExpression = substr(inStr.toStringType(), startPos, length)
