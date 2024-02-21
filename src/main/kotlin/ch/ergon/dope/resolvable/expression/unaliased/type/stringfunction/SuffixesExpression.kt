package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class SuffixesExpression(
    private val inStr: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(symbol = "SUFFIXES", inStr)
}

fun suffixes(inStr: TypeExpression<StringType>) = SuffixesExpression(inStr)

fun suffixes(inStr: String): SuffixesExpression = suffixes(inStr.toStringType())
