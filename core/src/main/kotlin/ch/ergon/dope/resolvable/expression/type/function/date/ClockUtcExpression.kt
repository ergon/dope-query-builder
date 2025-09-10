package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

data class ClockUtcExpression(val format: TypeExpression<StringType>? = null) :
    FunctionExpression<StringType>("CLOCK_UTC", listOf(format))

fun utcClockString(format: TypeExpression<StringType>? = null) = ClockUtcExpression(format)

fun utcClockString(format: String) = utcClockString(format.toDopeType())
