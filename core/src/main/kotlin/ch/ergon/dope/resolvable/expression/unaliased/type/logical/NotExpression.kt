package ch.ergon.dope.resolvable.expression.unaliased.type.logical

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.PrefixOperator
import ch.ergon.dope.validtype.BooleanType

class NotExpression(
    expression: TypeExpression<BooleanType>,
) : TypeExpression<BooleanType>, PrefixOperator("NOT", expression) {
    override fun toQueryString(): String = toPrefixQueryString()
}

fun not(expression: TypeExpression<BooleanType>) = NotExpression(expression)
