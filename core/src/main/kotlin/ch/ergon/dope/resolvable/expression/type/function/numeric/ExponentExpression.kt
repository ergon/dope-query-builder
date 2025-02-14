package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class ExponentExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("EXP", value)

fun exp(value: TypeExpression<NumberType>) = ExponentExpression(value)

fun exp(value: Number) = exp(value.toDopeType())
