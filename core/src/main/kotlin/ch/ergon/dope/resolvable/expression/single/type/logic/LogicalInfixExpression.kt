package ch.ergon.dope.resolvable.expression.single.type.logic

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.util.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType

sealed class LogicalInfixExpression(
    left: TypeExpression<BooleanType>,
    symbol: String,
    right: TypeExpression<BooleanType>,
) : TypeExpression<BooleanType>, InfixOperator(left, symbol, right) {
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(useBrackets = true, manager = manager)
}
