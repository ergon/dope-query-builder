package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class LtrimExpression(
    private val inStr: TypeExpression<StringType>,
    private val extra: TypeExpression<StringType> = " ".toStringType(),
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(symbol = "LTRIM", inStr, extra = extra)
}

fun ltrim(inStr: TypeExpression<StringType>, extra: TypeExpression<StringType> = " ".toStringType()): LtrimExpression =
    LtrimExpression(inStr, extra)

fun ltrim(inStr: TypeExpression<StringType>, extra: String): LtrimExpression = ltrim(inStr, extra.toStringType())

fun ltrim(inStr: String, extra: TypeExpression<StringType>): LtrimExpression = ltrim(inStr.toStringType(), extra)

fun ltrim(inStr: String, extra: String): LtrimExpression = ltrim(inStr.toStringType(), extra.toStringType())

fun ltrim(inStr: String): LtrimExpression = ltrim(inStr.toStringType())
