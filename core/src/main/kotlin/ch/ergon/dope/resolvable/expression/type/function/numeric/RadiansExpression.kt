package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class RadiansExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("RADIANS", value)

fun radians(value: TypeExpression<NumberType>) = RadiansExpression(value)

fun radians(value: Number) = radians(value.toDopeType())
