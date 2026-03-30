package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class SignExpression(val value: TypeExpression<NumberType>) : FunctionExpression<NumberType>(listOf(value))

fun sign(value: TypeExpression<NumberType>) = SignExpression(value)

fun sign(value: Number) = sign(value.toDopeType())
