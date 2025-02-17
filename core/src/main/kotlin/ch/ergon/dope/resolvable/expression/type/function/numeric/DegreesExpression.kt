package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class DegreesExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("DEGREES", value)

fun degrees(value: TypeExpression<NumberType>) = DegreesExpression(value)

fun degrees(value: Number) = degrees(value.toDopeType())
