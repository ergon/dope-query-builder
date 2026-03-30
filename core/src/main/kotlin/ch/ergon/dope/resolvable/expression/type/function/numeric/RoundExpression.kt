package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class RoundExpression(
    val value: TypeExpression<NumberType>,
    val digits: TypeExpression<NumberType>? = null,
) : FunctionExpression<NumberType>(listOf(value, digits))

fun round(value: TypeExpression<NumberType>) = RoundExpression(value)

fun round(value: Number) = round(value.toDopeType())

fun round(value: TypeExpression<NumberType>, digits: TypeExpression<NumberType>) = RoundExpression(value, digits)

fun round(value: TypeExpression<NumberType>, digits: Number) = round(value, digits.toDopeType())

fun round(value: Number, digits: TypeExpression<NumberType>) = round(value.toDopeType(), digits)

fun round(value: Number, digits: Number) = round(value.toDopeType(), digits.toDopeType())
