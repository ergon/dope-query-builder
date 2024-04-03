package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class NowStrExpression(
    private val format: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString("NOW_STR", format)
}

// todo: DOPE-177
fun nowStr(format: TypeExpression<StringType> = "".toStringType()): NowStrExpression = NowStrExpression(format)

fun nowStr(format: String): NowStrExpression = nowStr(format.toStringType())
