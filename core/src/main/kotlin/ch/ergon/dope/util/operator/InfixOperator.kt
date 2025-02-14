package ch.ergon.dope.util.operator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.util.formatToQueryStringWithBrackets
import ch.ergon.dope.util.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ValidType

open class InfixOperator(
    private val left: TypeExpression<out ValidType>,
    private val symbol: String,
    private val right: TypeExpression<out ValidType>,
) {
    fun toInfixDopeQuery(useBrackets: Boolean = false, manager: DopeQueryManager): DopeQuery {
        val leftDopeQuery = left.toDopeQuery(manager)
        val rightDopeQuery = right.toDopeQuery(manager)
        return if (useBrackets) {
            DopeQuery(
                queryString = formatToQueryStringWithBrackets(leftDopeQuery.queryString, symbol, rightDopeQuery.queryString),
                parameters = leftDopeQuery.parameters.merge(rightDopeQuery.parameters),
            )
        } else {
            DopeQuery(
                queryString = formatToQueryStringWithSymbol(leftDopeQuery.queryString, symbol, rightDopeQuery.queryString),
                parameters = leftDopeQuery.parameters.merge(rightDopeQuery.parameters),
            )
        }
    }
}
