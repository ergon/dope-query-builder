package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class ArcSineExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("ASIN", value)

fun asin(value: TypeExpression<NumberType>) = ArcSineExpression(value)

fun asin(value: Number) = asin(value.toDopeType())
