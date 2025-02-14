package ch.ergon.dope.resolvable.expression.single.type.function.numeric

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class CeilingExpression(value: TypeExpression<NumberType>) : NumberFunctionExpression("CEIL", value)

fun ceil(value: TypeExpression<NumberType>) = CeilingExpression(value)

fun ceil(value: Number) = ceil(value.toDopeType())
