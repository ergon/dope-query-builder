package ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class AbsoluteExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("ABS", value)

fun abs(value: TypeExpression<NumberType>) = AbsoluteExpression(value)

fun abs(value: Number) = abs(value.toDopeType())
