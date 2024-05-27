package ch.ergon.dope.resolvable.operator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier

interface FunctionOperator {
    fun toFunctionQueryString(symbol: String, vararg arguments: DopeQuery, extra: DopeQuery?) =
        if (extra == null) {
            toFunctionQueryString(symbol = symbol, *arguments)
        } else {
            toFunctionQueryString(symbol = symbol, *arguments, extra)
        }

    fun toFunctionQueryString(symbol: String, vararg arguments: DopeQuery) = arguments.joinToString(
        ", ",
        prefix = "$symbol(",
        postfix = ")",
    ) {
        it.queryString
    }

    fun toFunctionQueryString(symbol: String, quantifier: AggregateQuantifier, vararg arguments: DopeQuery) = arguments.joinToString(
        ", ",
        prefix = "$symbol($quantifier ",
        postfix = ")",
    ) {
        it.queryString
    }

    fun toFunctionQueryString(symbol: String, vararg arguments: String) = arguments.joinToString(
        ", ",
        prefix = "$symbol(",
        postfix = ")",
    )
}
