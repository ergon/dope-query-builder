package ch.ergon.dope.resolvable.expression.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.operator.PrefixOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class NegationExpression(
    numberExpression: TypeExpression<NumberType>,
) : TypeExpression<NumberType>, PrefixOperator("-", numberExpression) {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery = toPrefixDopeQuery(separator = "", manager)
}

fun neg(numberExpression: TypeExpression<NumberType>) = NegationExpression(numberExpression)

fun neg(number: Number): NegationExpression = neg(number.toDopeType())
