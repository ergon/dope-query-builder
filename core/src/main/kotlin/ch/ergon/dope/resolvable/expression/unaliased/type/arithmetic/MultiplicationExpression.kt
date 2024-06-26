package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class MultiplicationExpression(left: TypeExpression<NumberType>, right: TypeExpression<NumberType>) :
    NumberInfixExpression(left, "*", right)

fun TypeExpression<NumberType>.mul(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    MultiplicationExpression(this, numberExpression)

fun TypeExpression<NumberType>.mul(number: Number): NumberInfixExpression =
    mul(number.toDopeType())

fun Number.mul(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toDopeType().mul(numberExpression)

fun Number.mul(number: Number): NumberInfixExpression =
    this.toDopeType().mul(number.toDopeType())
