package ch.ergon.dope.resolvable.expression.type.logic

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.operator.InfixOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.BooleanType

sealed class LogicalInfixExpression(
    left: TypeExpression<BooleanType>,
    symbol: String,
    right: TypeExpression<BooleanType>,
) : TypeExpression<BooleanType>, InfixOperator(left, symbol, right) {
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(useBrackets = true, manager = manager)
}
