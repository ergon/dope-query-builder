package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class LogNaturalisExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("LN", value)

fun ln(value: TypeExpression<NumberType>) = LogNaturalisExpression(value)

fun ln(value: Number) = ln(value.toDopeType())
