package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class ArcTangentExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("ATAN", value)

fun atan(value: TypeExpression<NumberType>) = ArcTangentExpression(value)

fun atan(value: Number) = atan(value.toDopeType())
