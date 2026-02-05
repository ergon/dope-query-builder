package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.StringType

data class UpperExpression(val inStr: TypeExpression<StringType>) : FunctionExpression<StringType>(listOf(inStr))

fun TypeExpression<StringType>.upper() = UpperExpression(this)
