package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class LtrimExpression(
    private val inStr: TypeExpression<StringType>,
    private val extra: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(symbol = "LTRIM", inStr, extra)
}

fun ltrim(inStr: TypeExpression<StringType>, extra: TypeExpression<StringType>): LtrimExpression = LtrimExpression(inStr, extra)

fun ltrim(inStr: String, extra: String): LtrimExpression = ltrim(inStr.toStringType(), extra.toStringType())

fun ltrim(inStr: String, extra: Char): LtrimExpression = ltrim(inStr.toStringType(), extra.toString().toStringType())

fun ltrim(inStr: String): LtrimExpression = ltrim(inStr.toStringType(), " ".toStringType())

fun ltrim(inStr: TypeExpression<StringType>, extra: Char): LtrimExpression = ltrim(inStr, extra.toString().toStringType())

fun ltrim(inStr: TypeExpression<StringType>): LtrimExpression = ltrim(inStr, " ".toStringType())
