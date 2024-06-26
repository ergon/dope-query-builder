package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class TruncationExpression(value: TypeExpression<NumberType>, digits: TypeExpression<NumberType>? = null) :
    NumberFunctionExpression("TRUNC", value, digits)

fun trunc(value: TypeExpression<NumberType>) = TruncationExpression(value)
fun trunc(value: Number) = trunc(value.toNumberType())

fun trunc(value: TypeExpression<NumberType>, digits: TypeExpression<NumberType>) = TruncationExpression(value, digits)
fun trunc(value: TypeExpression<NumberType>, digits: Number) = trunc(value, digits.toNumberType())
fun trunc(value: Number, digits: TypeExpression<NumberType>) = trunc(value.toNumberType(), digits)
fun trunc(value: Number, digits: Number) = trunc(value.toNumberType(), digits.toNumberType())
