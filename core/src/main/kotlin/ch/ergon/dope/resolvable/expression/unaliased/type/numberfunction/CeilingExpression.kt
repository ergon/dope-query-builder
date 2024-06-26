package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class CeilingExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("CEIL", value)

fun ceil(value: TypeExpression<NumberType>) = CeilingExpression(value)
fun ceil(value: Number) = ceil(value.toNumberType())
