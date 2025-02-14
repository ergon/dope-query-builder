package ch.ergon.dope.resolvable.expression.single.type.function.numeric

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class RandomExpression(value: TypeExpression<NumberType>? = null) : NumberFunctionExpression("RANDOM", value)

fun random() = RandomExpression()

fun random(value: TypeExpression<NumberType>) = RandomExpression(value)

fun random(value: Number) = random(value.toDopeType())
