package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class SineExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("SIN", value)

fun sin(value: TypeExpression<NumberType>) = SineExpression(value)

fun sin(value: Number) = sin(value.toDopeType())
