package ch.ergon.dope.resolvable.expression.single.type.function.numeric

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class LogExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("LOG", value)

fun log(value: TypeExpression<NumberType>) = LogExpression(value)

fun log(value: Number) = log(value.toDopeType())
