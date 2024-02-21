package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class NowStrExpression(private val fmt: TypeExpression<StringType>) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = this.toFunctionQueryString("NOW_STR", fmt)
}

fun nowStr(fmt: TypeExpression<StringType>): NowStrExpression = NowStrExpression(fmt)

fun nowStr(): NowStrExpression = nowStr("".toStringType())

fun nowStr(fmt: String): NowStrExpression = nowStr(fmt.toStringType())
