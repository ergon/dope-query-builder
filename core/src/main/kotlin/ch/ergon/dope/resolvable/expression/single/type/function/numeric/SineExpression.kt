package ch.ergon.dope.resolvable.expression.single.type.function.numeric

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class SineExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("SIN", value)

fun sin(value: TypeExpression<NumberType>) = SineExpression(value)

fun sin(value: Number) = sin(value.toDopeType())
