package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class CeilingExpression(val value: TypeExpression<NumberType>) : FunctionExpression<NumberType>(listOf(value))

fun ceil(value: TypeExpression<NumberType>) = CeilingExpression(value)

fun ceil(value: Number) = ceil(value.toDopeType())
