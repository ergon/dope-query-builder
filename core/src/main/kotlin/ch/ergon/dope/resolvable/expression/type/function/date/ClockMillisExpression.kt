package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.NumberType

class ClockMillisExpression() : FunctionExpression<NumberType>("CLOCK_MILLIS")

fun clockMillis() = ClockMillisExpression()
