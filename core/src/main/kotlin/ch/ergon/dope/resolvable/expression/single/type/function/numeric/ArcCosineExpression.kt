package ch.ergon.dope.resolvable.expression.single.type.function.numeric

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class ArcCosineExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("ACOS", value)

fun acos(value: TypeExpression<NumberType>) = ArcCosineExpression(value)

fun acos(value: Number) = acos(value.toDopeType())
