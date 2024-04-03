package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class NowStrExpression(
    private val fmt: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString("NOW_STR", fmt)
}

// todo: DOPE-177
fun nowStr(fmt: TypeExpression<StringType> = "".toStringType()): NowStrExpression = NowStrExpression(fmt)

fun nowStr(fmt: String): NowStrExpression = nowStr(fmt.toStringType())
