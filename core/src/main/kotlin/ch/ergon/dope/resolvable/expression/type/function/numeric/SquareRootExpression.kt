package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class SquareRootExpression(val value: TypeExpression<NumberType>) : FunctionExpression<NumberType>(listOf(value))

fun sqrt(value: TypeExpression<NumberType>) = SquareRootExpression(value)

fun sqrt(value: Number) = sqrt(value.toDopeType())
