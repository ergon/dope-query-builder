package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class ClockTzExpression(timeZone: TypeExpression<StringType>, format: TypeExpression<StringType>? = null) :
    FunctionExpression<StringType>("CLOCK_TZ", timeZone, format)

fun formattedClockIn(timeZone: TypeExpression<StringType>, format: TypeExpression<StringType>? = null) = ClockTzExpression(timeZone, format)

fun formattedClockIn(timeZone: String, format: TypeExpression<StringType>? = null) = formattedClockIn(timeZone.toDopeType(), format)

fun formattedClockIn(timeZone: TypeExpression<StringType>, format: String) = formattedClockIn(timeZone, format.toDopeType())

fun formattedClockIn(timeZone: String, format: String) = formattedClockIn(timeZone.toDopeType(), format.toDopeType())
