package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.NumberType

class NowMillisExpression() : FunctionExpression<NumberType>("NOW_MILLIS")

fun nowEpochMillis() = NowMillisExpression()
