package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class PowerExpression(base: TypeExpression<NumberType>, exponent: TypeExpression<NumberType>) :
    NumberFunctionExpression("POWER", base, exponent)

fun power(base: TypeExpression<NumberType>, exponent: TypeExpression<NumberType>) = PowerExpression(base, exponent)
fun power(base: TypeExpression<NumberType>, exponent: Number) = power(base, exponent.toNumberType())
fun power(base: Number, exponent: TypeExpression<NumberType>) = power(base.toNumberType(), exponent)
fun power(base: Number, exponent: Number) = power(base.toNumberType(), exponent.toNumberType())
