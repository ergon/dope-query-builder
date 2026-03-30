package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class DegreesExpression(val value: TypeExpression<NumberType>) : FunctionExpression<NumberType>(listOf(value))

fun degrees(value: TypeExpression<NumberType>) = DegreesExpression(value)

fun degrees(value: Number) = degrees(value.toDopeType())
