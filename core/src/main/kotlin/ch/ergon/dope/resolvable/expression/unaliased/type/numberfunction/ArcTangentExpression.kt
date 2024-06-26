package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class ArcTangentExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("ATAN", value)

fun atan(value: TypeExpression<NumberType>) = ArcTangentExpression(value)
fun atan(value: Number) = atan(value.toNumberType())
