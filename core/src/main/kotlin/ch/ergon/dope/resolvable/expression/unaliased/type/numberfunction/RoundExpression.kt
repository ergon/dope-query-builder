package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class RoundExpression(value: TypeExpression<NumberType>, digits: TypeExpression<NumberType>? = null) :
    NumberFunctionExpression("ROUND", value, digits)

fun round(value: TypeExpression<NumberType>) = RoundExpression(value)

fun round(value: Number) = round(value.toNumberType())

fun round(value: TypeExpression<NumberType>, digits: TypeExpression<NumberType>) = RoundExpression(value, digits)

fun round(value: TypeExpression<NumberType>, digits: Number) = round(value, digits.toNumberType())

fun round(value: Number, digits: TypeExpression<NumberType>) = round(value.toNumberType(), digits)

fun round(value: Number, digits: Number) = round(value.toNumberType(), digits.toNumberType())
