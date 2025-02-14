package ch.ergon.dope.resolvable.expression.single.type.function.numeric

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class ExponentExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("EXP", value)

fun exp(value: TypeExpression<NumberType>) = ExponentExpression(value)

fun exp(value: Number) = exp(value.toDopeType())
