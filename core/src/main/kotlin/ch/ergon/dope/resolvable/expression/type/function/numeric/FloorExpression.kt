package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class FloorExpression(override val value: TypeExpression<NumberType>) : NumberFunctionExpression("FLOOR", value)

fun floor(value: TypeExpression<NumberType>) = FloorExpression(value)

fun floor(value: Number) = floor(value.toDopeType())
