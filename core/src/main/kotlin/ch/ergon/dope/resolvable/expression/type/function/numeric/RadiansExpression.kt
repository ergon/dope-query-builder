package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class RadiansExpression(val value: TypeExpression<NumberType>) : FunctionExpression<NumberType>(listOf(value))

fun radians(value: TypeExpression<NumberType>) = RadiansExpression(value)

fun radians(value: Number) = radians(value.toDopeType())
