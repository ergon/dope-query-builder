package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class CosineExpression(override val value: TypeExpression<NumberType>) : NumberFunctionExpression("COS", value)

fun cos(value: TypeExpression<NumberType>) = CosineExpression(value)

fun cos(value: Number) = cos(value.toDopeType())
