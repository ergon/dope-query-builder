package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class LogNaturalisExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("LN", value)

fun ln(value: TypeExpression<NumberType>) = LogNaturalisExpression(value)
fun ln(value: Number) = ln(value.toDopeType())
