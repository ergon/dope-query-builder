package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class ClockStringExpression(format: TypeExpression<StringType>? = null) : FunctionExpression<StringType>("CLOCK_STR", format)

fun clockString(format: TypeExpression<StringType>? = null) = ClockStringExpression(format)

fun clockString(format: String) = clockString(format.toDopeType())
