package ch.ergon.dope.resolvable.expression.unaliased.type.numeric

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class ArcTangentExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("ATAN", value)

fun atan(value: TypeExpression<NumberType>) = ArcTangentExpression(value)

fun atan(value: Number) = atan(value.toDopeType())
