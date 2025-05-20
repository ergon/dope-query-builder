package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.StringType

class NowStrExpression(format: TypeExpression<StringType>? = null) : FunctionExpression<StringType>("NOW_STR", format)

fun nowString(format: TypeExpression<StringType>? = null) = NowStrExpression(format)
