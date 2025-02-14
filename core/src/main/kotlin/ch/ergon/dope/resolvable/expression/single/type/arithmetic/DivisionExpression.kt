package ch.ergon.dope.resolvable.expression.single.type.arithmetic

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class DivisionExpression(left: TypeExpression<NumberType>, right: TypeExpression<NumberType>) :
    NumberInfixExpression(left, "/", right)

fun TypeExpression<NumberType>.div(numberExpression: TypeExpression<NumberType>) = DivisionExpression(this, numberExpression)

fun TypeExpression<NumberType>.div(number: Number) = div(number.toDopeType())

fun Number.div(numberExpression: TypeExpression<NumberType>) = toDopeType().div(numberExpression)
