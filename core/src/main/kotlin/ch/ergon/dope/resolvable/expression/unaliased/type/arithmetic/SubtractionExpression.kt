package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class SubtractionExpression(left: TypeExpression<NumberType>, right: TypeExpression<NumberType>) :
    NumberInfixExpression(left, "-", right)

fun TypeExpression<NumberType>.sub(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    SubtractionExpression(this, numberExpression)

fun TypeExpression<NumberType>.sub(number: Number): NumberInfixExpression =
    sub(number.toNumberType())

fun Number.sub(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toNumberType().sub(numberExpression)

fun Number.sub(number: Number): NumberInfixExpression =
    this.toNumberType().sub(number.toNumberType())
