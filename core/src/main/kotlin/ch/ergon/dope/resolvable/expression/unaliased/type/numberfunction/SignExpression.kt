package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class SignExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("SIGN", value)

fun sign(value: TypeExpression<NumberType>) = SignExpression(value)
fun sign(value: Number) = sign(value.toNumberType())
