package ch.ergon.dope.couchbase.resolver.expression

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.AbstractCouchbaseResolver
import ch.ergon.dope.couchbase.util.formatToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatToQueryStringWithSymbol
import ch.ergon.dope.resolvable.expression.operator.InfixOperator
import ch.ergon.dope.resolvable.expression.type.arithmetic.NumberInfixExpression
import ch.ergon.dope.resolvable.expression.type.logic.LogicalInfixExpression

interface InfixOperatorResolver : AbstractCouchbaseResolver {
    fun resolve(operator: InfixOperator<*>) =
        when (operator) {
            is NumberInfixExpression, is LogicalInfixExpression -> {
                val left = operator.left.toDopeQuery(this)
                val right = operator.right.toDopeQuery(this)
                CouchbaseDopeQuery(
                    formatToQueryStringWithBrackets(left.queryString, operator.symbol, right.queryString),
                    left.parameters.merge(right.parameters),
                )
            }

            else -> {
                val left = operator.left.toDopeQuery(this)
                val right = operator.right.toDopeQuery(this)
                val operatorQueryString = formatToQueryStringWithSymbol(left.queryString, operator.symbol, right.queryString)
                CouchbaseDopeQuery(operatorQueryString, left.parameters.merge(right.parameters))
            }
        }
}
