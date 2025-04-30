package ch.ergon.dope.resolvable.expression.type.arithmetic

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class MultiplicationExpression(left: TypeExpression<NumberType>, right: TypeExpression<NumberType>) :
    NumberInfixExpression(left, "*", right)

fun TypeExpression<NumberType>.mul(numberExpression: TypeExpression<NumberType>) = MultiplicationExpression(this, numberExpression)

fun TypeExpression<NumberType>.mul(number: Number) = mul(number.toDopeType())

fun Number.mul(numberExpression: TypeExpression<NumberType>) = toDopeType().mul(numberExpression)

fun Number.mul(number: Number) = toDopeType().mul(number.toDopeType())
