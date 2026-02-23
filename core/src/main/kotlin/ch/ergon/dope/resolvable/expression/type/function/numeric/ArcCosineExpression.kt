package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class ArcCosineExpression(val value: TypeExpression<NumberType>) : FunctionExpression<NumberType>(listOf(value))

fun acos(value: TypeExpression<NumberType>) = ArcCosineExpression(value)

fun acos(value: Number) = acos(value.toDopeType())
