package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

data class ClockStringExpression(val format: TypeExpression<StringType>? = null) :
    FunctionExpression<StringType>(listOf(format))

fun clockString(format: TypeExpression<StringType>? = null) = ClockStringExpression(format)

fun clockString(format: String) = clockString(format.toDopeType())
