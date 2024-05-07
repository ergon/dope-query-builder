package ch.ergon.dope.resolvable.operator

import ch.ergon.dope.DopeQuery

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

    fun toFunctionQueryString(symbol: String, vararg arguments: String) = arguments.joinToString(
        ", ",
        prefix = "$symbol(",
        postfix = ")",
    )
}
