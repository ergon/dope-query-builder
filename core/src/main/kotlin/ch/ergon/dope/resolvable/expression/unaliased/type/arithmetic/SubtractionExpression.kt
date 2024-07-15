package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class SubtractionExpression(left: TypeExpression<NumberType>, right: TypeExpression<NumberType>) :
    NumberInfixExpression(left, "-", right)

fun TypeExpression<NumberType>.sub(numberExpression: TypeExpression<NumberType>) = SubtractionExpression(this, numberExpression)

fun TypeExpression<NumberType>.sub(number: Number) = sub(number.toDopeType())

fun Number.sub(numberExpression: TypeExpression<NumberType>) = toDopeType().sub(numberExpression)

fun Number.sub(number: Number) = toDopeType().sub(number.toDopeType())
