package ch.ergon.dope.resolvable.expression.single.type.function.numeric

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class SignExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("SIGN", value)

fun sign(value: TypeExpression<NumberType>) = SignExpression(value)

fun sign(value: Number) = sign(value.toDopeType())
