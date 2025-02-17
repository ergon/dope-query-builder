package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class ArcCosineExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("ACOS", value)

fun acos(value: TypeExpression<NumberType>) = ArcCosineExpression(value)

fun acos(value: Number) = acos(value.toDopeType())
