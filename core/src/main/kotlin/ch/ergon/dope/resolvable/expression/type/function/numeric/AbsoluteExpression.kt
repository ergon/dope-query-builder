package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class AbsoluteExpression(val value: TypeExpression<NumberType>) : FunctionExpression<NumberType>(listOf(value))

fun abs(value: TypeExpression<NumberType>) = AbsoluteExpression(value)

fun abs(value: Number) = abs(value.toDopeType())
