package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryStringWithBrackets
import ch.ergon.dope.resolvable.operator.InfixOperator
import ch.ergon.dope.validtype.NumberType

sealed class NumberInfixExpression(
    private val left: TypeExpression<NumberType>,
    private val symbol: String,
    private val right: TypeExpression<NumberType>,
) : TypeExpression<NumberType>, InfixOperator(left, symbol, right) {
    override fun toDopeQuery(): DopeQuery {
        val leftDopeQuery = left.toDopeQuery()
        val rightDopeQuery = right.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryStringWithBrackets(leftDopeQuery.queryString, symbol, rightDopeQuery.queryString),
            parameters = leftDopeQuery.parameters + rightDopeQuery.parameters,
        )
    }
}
