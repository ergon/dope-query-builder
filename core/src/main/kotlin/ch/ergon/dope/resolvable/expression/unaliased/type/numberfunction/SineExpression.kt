package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class SineExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("SIN", value)

fun sin(value: TypeExpression<NumberType>) = SineExpression(value)
fun sin(value: Number) = sin(value.toDopeType())
