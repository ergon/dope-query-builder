package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class RandomExpression(val value: TypeExpression<NumberType>? = null) : FunctionExpression<NumberType>(listOfNotNull(value))

fun random() = RandomExpression()

fun random(value: TypeExpression<NumberType>) = RandomExpression(value)

fun random(value: Number) = random(value.toDopeType())
