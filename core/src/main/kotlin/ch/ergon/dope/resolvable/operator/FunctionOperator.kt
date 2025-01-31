package ch.ergon.dope.resolvable.operator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.formatListToQueryStringWithBrackets
import ch.ergon.dope.resolvable.formatStringListToQueryStringWithBrackets

interface FunctionOperator {
    fun toFunctionQueryString(symbol: String, vararg arguments: DopeQuery?) =
        formatListToQueryStringWithBrackets(arguments.filterNotNull(), prefix = "$symbol(")

    fun toFunctionQueryString(symbol: String, vararg arguments: String) =
        formatStringListToQueryStringWithBrackets(arguments.toList(), prefix = "$symbol(")
}
