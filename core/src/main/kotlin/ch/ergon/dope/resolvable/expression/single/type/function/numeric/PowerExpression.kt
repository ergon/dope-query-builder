package ch.ergon.dope.resolvable.expression.single.type.function.numeric

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class PowerExpression(base: TypeExpression<NumberType>, exponent: TypeExpression<NumberType>) :
    NumberFunctionExpression("POWER", base, exponent)

fun power(base: TypeExpression<NumberType>, exponent: TypeExpression<NumberType>) = PowerExpression(base, exponent)

fun power(base: TypeExpression<NumberType>, exponent: Number) = power(base, exponent.toDopeType())

fun power(base: Number, exponent: TypeExpression<NumberType>) = power(base.toDopeType(), exponent)

fun power(base: Number, exponent: Number) = power(base.toDopeType(), exponent.toDopeType())
