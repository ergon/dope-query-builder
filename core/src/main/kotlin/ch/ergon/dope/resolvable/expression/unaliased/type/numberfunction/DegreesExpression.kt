package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class DegreesExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("DEGREES", value)

fun degrees(value: TypeExpression<NumberType>) = DegreesExpression(value)
fun degrees(value: Number) = degrees(value.toNumberType())
