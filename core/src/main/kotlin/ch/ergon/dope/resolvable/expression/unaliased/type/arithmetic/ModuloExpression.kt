package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class ModuloExpression(left: TypeExpression<NumberType>, right: TypeExpression<NumberType>) :
    NumberInfixExpression(left, "%", right)

fun TypeExpression<NumberType>.mod(numberExpression: TypeExpression<NumberType>) = ModuloExpression(this, numberExpression)

fun TypeExpression<NumberType>.mod(number: Number) = ModuloExpression(this, number.toDopeType())

fun Number.mod(numberExpression: TypeExpression<NumberType>) = toDopeType().mod(numberExpression)

fun Number.mod(number: Number) = toDopeType().mod(number.toDopeType())
