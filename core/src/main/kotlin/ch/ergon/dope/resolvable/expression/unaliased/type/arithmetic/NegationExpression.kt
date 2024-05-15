package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.operator.PrefixOperator
import ch.ergon.dope.validtype.NumberType

class NegationExpression(
    numberExpression: TypeExpression<NumberType>,
) : TypeExpression<NumberType>, PrefixOperator("-", numberExpression) {
    override fun toQuery(): DopeQuery = toPrefixQueryString(separator = "")
}

fun neg(numberExpression: TypeExpression<NumberType>): NegationExpression =
    NegationExpression(numberExpression)

fun neg(number: Number): NegationExpression = neg(number.toNumberType())
