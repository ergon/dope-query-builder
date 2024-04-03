package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

// Argument is called char, but is a string..
class TrimExpression(
    private val inStr: TypeExpression<StringType>,
    private val char: TypeExpression<StringType>? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(symbol = "TRIM", inStr, extra = char)
}

fun trim(inStr: TypeExpression<StringType>, char: TypeExpression<StringType>? = null) = TrimExpression(inStr, char)

fun trim(inStr: TypeExpression<StringType>, char: Char) = trim(inStr, char.toString().toStringType())

fun trim(inStr: String, char: TypeExpression<StringType>? = null) = trim(inStr.toStringType(), char)

fun trim(inStr: String, char: String) = trim(inStr.toStringType(), char.toStringType())

fun trim(inStr: String, char: Char) = trim(inStr.toStringType(), char.toString().toStringType())
