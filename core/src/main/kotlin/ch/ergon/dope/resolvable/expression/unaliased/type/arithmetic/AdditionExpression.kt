package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

class AdditionExpression(left: TypeExpression<NumberType>, right: TypeExpression<NumberType>) :
    NumberInfixExpression(left, "+", right)

fun TypeExpression<NumberType>.add(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    AdditionExpression(this, numberExpression)

fun TypeExpression<NumberType>.add(number: Number): NumberInfixExpression =
    add(number.toNumberType())

fun Number.add(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toNumberType().add(numberExpression)

fun Number.add(number: Number): NumberInfixExpression =
    this.toNumberType().add(number.toNumberType())
