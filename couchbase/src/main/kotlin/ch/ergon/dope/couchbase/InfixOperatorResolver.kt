package ch.ergon.dope.couchbase

import ch.ergon.dope.couchbase.util.formatToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatToQueryStringWithSymbol
import ch.ergon.dope.resolvable.expression.operator.InfixOperator
import ch.ergon.dope.resolvable.expression.type.arithmetic.NumberInfixExpression
import ch.ergon.dope.resolvable.expression.type.logic.LogicalInfixExpression

internal object InfixOperatorResolver {
    fun resolve(resolver: CouchbaseResolver, operator: InfixOperator) =
        when (operator) {
            is NumberInfixExpression -> {
                val left = operator.left.toDopeQuery(resolver)
                val right = operator.right.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    formatToQueryStringWithBrackets(left.queryString, operator.symbol, right.queryString),
                    left.parameters.merge(right.parameters),
                )
            }

            is LogicalInfixExpression -> {
                val left = operator.left.toDopeQuery(resolver)
                val right = operator.right.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    formatToQueryStringWithBrackets(left.queryString, operator.symbol, right.queryString),
                    left.parameters.merge(right.parameters),
                )
            }

            else -> {
                val left = operator.left.toDopeQuery(resolver)
                val right = operator.right.toDopeQuery(resolver)
                val operatorQueryString = formatToQueryStringWithSymbol(left.queryString, operator.symbol, right.queryString)
                CouchbaseDopeQuery(operatorQueryString, left.parameters.merge(right.parameters))
            }
        }
}
