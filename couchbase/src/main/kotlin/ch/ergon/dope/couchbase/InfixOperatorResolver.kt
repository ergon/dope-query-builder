package ch.ergon.dope.couchbase

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.couchbase.util.formatToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatToQueryStringWithSymbol
import ch.ergon.dope.resolvable.expression.operator.InfixOperator
import ch.ergon.dope.resolvable.expression.type.arithmetic.NumberInfixExpression
import ch.ergon.dope.resolvable.expression.type.logic.LogicalInfixExpression

internal object InfixOperatorResolver {
    fun resolve(manager: DopeQueryManager<CouchbaseDopeQuery>, operator: InfixOperator) =
        when (operator) {
            is NumberInfixExpression -> {
                val left = operator.left.toDopeQuery(manager)
                val right = operator.right.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    formatToQueryStringWithBrackets(left.queryString, operator.symbol, right.queryString),
                    left.parameters.merge(right.parameters),
                )
            }

            is LogicalInfixExpression -> {
                val left = operator.left.toDopeQuery(manager)
                val right = operator.right.toDopeQuery(manager)
                CouchbaseDopeQuery(
                    formatToQueryStringWithBrackets(left.queryString, operator.symbol, right.queryString),
                    left.parameters.merge(right.parameters),
                )
            }

            else -> {
                val left = operator.left.toDopeQuery(manager)
                val right = operator.right.toDopeQuery(manager)
                val useBrackets = operator is LogicalInfixExpression || operator is NumberInfixExpression
                val operatorQueryString = if (useBrackets) {
                    formatToQueryStringWithBrackets(left.queryString, operator.symbol, right.queryString)
                } else {
                    formatToQueryStringWithSymbol(left.queryString, operator.symbol, right.queryString)
                }
                CouchbaseDopeQuery(operatorQueryString, left.parameters.merge(right.parameters))
            }
        }
}
