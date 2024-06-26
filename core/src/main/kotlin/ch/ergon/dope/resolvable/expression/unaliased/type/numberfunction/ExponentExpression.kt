package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class ExponentExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("EXP", value)

fun exp(value: TypeExpression<NumberType>) = ExponentExpression(value)
fun exp(value: Number) = exp(value.toNumberType())
