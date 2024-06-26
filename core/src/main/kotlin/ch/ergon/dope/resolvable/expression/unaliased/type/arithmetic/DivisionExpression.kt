package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class DivisionExpression(left: TypeExpression<NumberType>, right: TypeExpression<NumberType>) :
    NumberInfixExpression(left, "/", right)

fun TypeExpression<NumberType>.div(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    DivisionExpression(this, numberExpression)

fun TypeExpression<NumberType>.div(number: Number): NumberInfixExpression =
    div(number.toDopeType())

fun Number.div(numberExpression: TypeExpression<NumberType>): NumberInfixExpression =
    this.toDopeType().div(numberExpression)
