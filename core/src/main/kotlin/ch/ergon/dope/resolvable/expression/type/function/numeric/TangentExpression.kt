package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class TangentExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("TAN", value)

fun tan(value: TypeExpression<NumberType>) = TangentExpression(value)

fun tan(value: Number) = tan(value.toDopeType())
