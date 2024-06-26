package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class CosineExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("COS", value)

fun cos(value: TypeExpression<NumberType>) = CosineExpression(value)
fun cos(value: Number) = cos(value.toNumberType())
