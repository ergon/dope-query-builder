package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class SquareRootExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("SQRT", value)

fun sqrt(value: TypeExpression<NumberType>) = SquareRootExpression(value)

fun sqrt(value: Number) = sqrt(value.toDopeType())
