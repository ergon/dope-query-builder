package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class ClockLocalExpression(format: TypeExpression<StringType>? = null) : FunctionExpression<StringType>("CLOCK_LOCAL", format)

fun localClockString(format: TypeExpression<StringType>? = null) = ClockLocalExpression(format)

fun localClockString(format: String) = localClockString(format.toDopeType())
