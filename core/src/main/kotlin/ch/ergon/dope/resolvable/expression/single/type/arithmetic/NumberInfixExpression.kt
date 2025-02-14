package ch.ergon.dope.resolvable.expression.single.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.util.formatToQueryStringWithBrackets
import ch.ergon.dope.util.operator.InfixOperator
import ch.ergon.dope.validtype.NumberType

sealed class NumberInfixExpression(
    private val left: TypeExpression<NumberType>,
    private val symbol: String,
    private val right: TypeExpression<NumberType>,
) : TypeExpression<NumberType>, InfixOperator(left, symbol, right) {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val leftDopeQuery = left.toDopeQuery(manager)
        val rightDopeQuery = right.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithBrackets(leftDopeQuery.queryString, symbol, rightDopeQuery.queryString),
            parameters = leftDopeQuery.parameters.merge(rightDopeQuery.parameters),
        )
    }
}
