package ch.ergon.dope.resolvable.operator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.resolvable.formatToQueryStringWithBrackets
import ch.ergon.dope.validtype.ValidType

open class InfixOperator(
    private val left: TypeExpression<out ValidType>,
    private val symbol: String,
    private val right: TypeExpression<out ValidType>,
) {
    fun toInfixQuery(useBrackets: Boolean = false): DopeQuery {
        val leftDopeQuery = left.toQuery()
        val rightDopeQuery = right.toQuery()
        return if (useBrackets) {
            DopeQuery(
                queryString = formatToQueryStringWithBrackets(leftDopeQuery.queryString, symbol, rightDopeQuery.queryString),
                parameters = leftDopeQuery.parameters + rightDopeQuery.parameters,
            )
        } else {
            DopeQuery(
                queryString = formatToQueryString(leftDopeQuery.queryString, symbol, rightDopeQuery.queryString),
                parameters = leftDopeQuery.parameters + rightDopeQuery.parameters,
            )
        }
    }
}
