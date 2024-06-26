package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class LogExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("LOG", value)

fun log(value: TypeExpression<NumberType>) = LogExpression(value)
fun log(value: Number) = log(value.toNumberType())
