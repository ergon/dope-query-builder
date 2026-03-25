package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class ArcSineExpression(val value: TypeExpression<NumberType>) : FunctionExpression<NumberType>(listOf(value))

fun asin(value: TypeExpression<NumberType>) = ArcSineExpression(value)

fun asin(value: Number) = asin(value.toDopeType())
