package ch.ergon.dope.resolvable.expression.unaliased.type.logical

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.BooleanType

sealed class LogicalInfixExpression(
    left: TypeExpression<BooleanType>,
    symbol: String,
    right: TypeExpression<BooleanType>,
) : TypeExpression<BooleanType>, InfixOperator(left, symbol, right) {
    override fun toDopeQuery(manager: DopeQueryManager) = toInfixDopeQuery(useBrackets = true, manager = manager)
}
