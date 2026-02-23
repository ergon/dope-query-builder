package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class TruncationExpression(
    val value: TypeExpression<NumberType>,
    val digits: TypeExpression<NumberType>? = null,
) : FunctionExpression<NumberType>(listOf(value, digits))

fun trunc(value: TypeExpression<NumberType>) = TruncationExpression(value)

fun trunc(value: Number) = trunc(value.toDopeType())

fun trunc(value: TypeExpression<NumberType>, digits: TypeExpression<NumberType>) = TruncationExpression(value, digits)

fun trunc(value: TypeExpression<NumberType>, digits: Number) = trunc(value, digits.toDopeType())

fun trunc(value: Number, digits: TypeExpression<NumberType>) = trunc(value.toDopeType(), digits)

fun trunc(value: Number, digits: Number) = trunc(value.toDopeType(), digits.toDopeType())
