package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class ArcSineExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("ASIN", value)

fun asin(value: TypeExpression<NumberType>) = ArcSineExpression(value)
fun asin(value: Number) = asin(value.toNumberType())
