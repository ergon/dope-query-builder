package ch.ergon.dope.resolvable.operator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatMinimumTwoToQueryString
import ch.ergon.dope.validtype.ValidType

open class InfixOperator(
    private val left: TypeExpression<out ValidType>,
    private val symbol: String,
    private val right: TypeExpression<out ValidType>,
) {
    fun toInfixQuery(): DopeQuery {
        val leftDopeQuery = left.toQuery()
        val rightDopeQuery = right.toQuery()
        return DopeQuery(
            queryString = formatMinimumTwoToQueryString(leftDopeQuery.queryString, symbol, rightDopeQuery.queryString),
            parameters = leftDopeQuery.parameters + rightDopeQuery.parameters,
        )
    }
}
