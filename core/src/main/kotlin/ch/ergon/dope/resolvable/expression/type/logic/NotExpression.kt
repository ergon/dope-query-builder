package ch.ergon.dope.resolvable.expression.type.logic

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.operator.PrefixOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType

class NotExpression(
    expression: TypeExpression<BooleanType>,
) : TypeExpression<BooleanType>, PrefixOperator("NOT", expression) {
    override fun toDopeQuery(manager: DopeQueryManager) = toPrefixDopeQuery(separator = " ", manager = manager)
}

fun not(expression: TypeExpression<BooleanType>) = NotExpression(expression)

fun not(boolean: Boolean) = not(boolean.toDopeType())
