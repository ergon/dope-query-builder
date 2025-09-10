package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

data class ClockLocalExpression(val format: TypeExpression<StringType>? = null) :
    FunctionExpression<StringType>("CLOCK_LOCAL", listOf(format))

fun localClockString(format: TypeExpression<StringType>? = null) = ClockLocalExpression(format)

fun localClockString(format: String) = localClockString(format.toDopeType())
