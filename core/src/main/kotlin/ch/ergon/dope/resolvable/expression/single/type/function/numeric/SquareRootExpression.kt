package ch.ergon.dope.resolvable.expression.single.type.function.numeric

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class SquareRootExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("SQRT", value)

fun sqrt(value: TypeExpression<NumberType>) = SquareRootExpression(value)

fun sqrt(value: Number) = sqrt(value.toDopeType())
