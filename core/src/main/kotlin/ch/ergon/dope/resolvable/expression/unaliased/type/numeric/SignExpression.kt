package ch.ergon.dope.resolvable.expression.unaliased.type.numeric

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class SignExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("SIGN", value)

fun sign(value: TypeExpression<NumberType>) = SignExpression(value)

fun sign(value: Number) = sign(value.toDopeType())
