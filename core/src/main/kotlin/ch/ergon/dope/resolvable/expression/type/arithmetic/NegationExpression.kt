package ch.ergon.dope.resolvable.expression.type.arithmetic

import ch.ergon.dope.resolvable.expression.operator.PrefixOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class NegationExpression(
    val numberExpression: TypeExpression<NumberType>,
) : TypeExpression<NumberType>, PrefixOperator(numberExpression)

fun neg(numberExpression: TypeExpression<NumberType>) = NegationExpression(numberExpression)

fun neg(number: Number): NegationExpression = neg(number.toDopeType())
