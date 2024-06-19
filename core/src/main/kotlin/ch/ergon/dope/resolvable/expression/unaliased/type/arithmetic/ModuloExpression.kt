package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class ModuloExpression(left: TypeExpression<NumberType>, right: TypeExpression<NumberType>) :
    NumberInfixExpression(left, "%", right)

fun TypeExpression<NumberType>.mod(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    ModuloExpression(this, numberExpression)

fun TypeExpression<NumberType>.mod(number: Number): NumberInfixExpression =
    ModuloExpression(this, number.toNumberType())

fun Number.mod(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toNumberType().mod(numberExpression)

fun Number.mod(number: Number): NumberInfixExpression =
    this.toNumberType().mod(number.toNumberType())
