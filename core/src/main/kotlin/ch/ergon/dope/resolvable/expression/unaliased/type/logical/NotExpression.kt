package ch.ergon.dope.resolvable.expression.unaliased.type.logical

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toBooleanType
import ch.ergon.dope.resolvable.operator.PrefixOperator
import ch.ergon.dope.validtype.BooleanType

class NotExpression(
    expression: TypeExpression<BooleanType>,
) : TypeExpression<BooleanType>, PrefixOperator("NOT", expression) {
    override fun toQuery(): DopeQuery = toPrefixQueryString()
}

fun not(expression: TypeExpression<BooleanType>) = NotExpression(expression)

fun not(boolean: Boolean) = not(boolean.toBooleanType())
