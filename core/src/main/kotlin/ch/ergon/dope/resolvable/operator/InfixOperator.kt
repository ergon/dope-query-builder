package ch.ergon.dope.resolvable.operator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatMinimumTwoToQueryString
import ch.ergon.dope.resolvable.formatToQueryStringWithBrackets
import ch.ergon.dope.validtype.ValidType

open class InfixOperator(
    private val left: TypeExpression<out ValidType>,
    private val symbol: String,
    private val right: TypeExpression<out ValidType>,
) {
    fun toInfixDopeQuery(useBrackets: Boolean = false): DopeQuery {
        val leftDopeQuery = left.toDopeQuery()
        val rightDopeQuery = right.toDopeQuery()
        return if (useBrackets) {
            DopeQuery(
                queryString = formatToQueryStringWithBrackets(leftDopeQuery.queryString, symbol, rightDopeQuery.queryString),
                parameters = leftDopeQuery.parameters + rightDopeQuery.parameters,
            )
        } else {
            DopeQuery(
                queryString = formatMinimumTwoToQueryString(leftDopeQuery.queryString, symbol, rightDopeQuery.queryString),
                parameters = leftDopeQuery.parameters + rightDopeQuery.parameters,
            )
        }
    }
}
