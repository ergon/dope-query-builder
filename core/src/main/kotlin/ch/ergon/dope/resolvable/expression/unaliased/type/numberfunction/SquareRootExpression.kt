package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class SquareRootExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("SQRT", value)

fun sqrt(value: TypeExpression<NumberType>) = SquareRootExpression(value)
fun sqrt(value: Number) = sqrt(value.toNumberType())
