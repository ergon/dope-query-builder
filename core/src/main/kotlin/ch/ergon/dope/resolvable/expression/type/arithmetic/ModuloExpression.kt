package ch.ergon.dope.resolvable.expression.type.arithmetic

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class ModuloExpression(override val left: TypeExpression<NumberType>, override val right: TypeExpression<NumberType>) :
    NumberInfixExpression(left, right)

fun TypeExpression<NumberType>.mod(numberExpression: TypeExpression<NumberType>) = ModuloExpression(this, numberExpression)

fun TypeExpression<NumberType>.mod(number: Number) = ModuloExpression(this, number.toDopeType())

fun Number.mod(numberExpression: TypeExpression<NumberType>) = toDopeType().mod(numberExpression)

fun Number.mod(number: Number) = toDopeType().mod(number.toDopeType())
