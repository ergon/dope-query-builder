package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class ArcCosineExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("ACOS", value)

fun acos(value: TypeExpression<NumberType>) = ArcCosineExpression(value)
fun acos(value: Number) = acos(value.toNumberType())
