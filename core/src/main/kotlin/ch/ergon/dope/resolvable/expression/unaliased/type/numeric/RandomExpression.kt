package ch.ergon.dope.resolvable.expression.unaliased.type.numeric

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class RandomExpression(value: TypeExpression<NumberType>? = null) : NumberFunctionExpression("RANDOM", value)

fun random() = RandomExpression()

fun random(value: TypeExpression<NumberType>) = RandomExpression(value)

fun random(value: Number) = random(value.toDopeType())
