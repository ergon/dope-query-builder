package ch.ergon.dope.resolvable.expression.single.type.arithmetic

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class AdditionExpression(left: TypeExpression<NumberType>, right: TypeExpression<NumberType>) :
    NumberInfixExpression(left, "+", right)

fun TypeExpression<NumberType>.add(numberExpression: TypeExpression<NumberType>) = AdditionExpression(this, numberExpression)

fun TypeExpression<NumberType>.add(number: Number) = add(number.toDopeType())

fun Number.add(numberExpression: TypeExpression<NumberType>) = toDopeType().add(numberExpression)

fun Number.add(number: Number) = toDopeType().add(number.toDopeType())
