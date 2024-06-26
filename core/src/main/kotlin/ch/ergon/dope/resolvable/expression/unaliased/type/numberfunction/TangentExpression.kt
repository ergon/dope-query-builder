package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class TangentExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("TAN", value)

fun tan(value: TypeExpression<NumberType>) = TangentExpression(value)
fun tan(value: Number) = tan(value.toNumberType())
