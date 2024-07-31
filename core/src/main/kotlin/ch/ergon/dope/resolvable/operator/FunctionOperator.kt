package ch.ergon.dope.resolvable.operator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier

interface FunctionOperator {
    fun toFunctionQueryString(symbol: String, vararg arguments: DopeQuery?) = arguments.filterNotNull().joinToString(
        ", ",
        prefix = "$symbol(",
        postfix = ")",
    ) {
        it.queryString
    }

    fun toFunctionQueryString(symbol: String, vararg arguments: String) = arguments.joinToString(
        ", ",
        prefix = "$symbol(",
        postfix = ")",
    )

    fun toFunctionQueryString(symbol: String, quantifier: AggregateQuantifier?, argument: String) =
        quantifier?.let {
            "$symbol($quantifier $argument)"
        } ?: toFunctionQueryString(symbol, argument)
}
