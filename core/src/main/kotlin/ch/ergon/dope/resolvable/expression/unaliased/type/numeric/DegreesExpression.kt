package ch.ergon.dope.resolvable.expression.unaliased.type.numeric

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class DegreesExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("DEGREES", value)

fun degrees(value: TypeExpression<NumberType>) = DegreesExpression(value)

fun degrees(value: Number) = degrees(value.toDopeType())
