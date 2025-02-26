package ch.ergon.dope.resolvable.expression.operator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.util.formatListToQueryStringWithBrackets
import ch.ergon.dope.util.formatStringListToQueryStringWithBrackets

interface FunctionOperator {
    fun toFunctionQueryString(symbol: String, vararg arguments: DopeQuery?) =
        formatListToQueryStringWithBrackets(arguments.filterNotNull(), prefix = "$symbol(")

    fun toFunctionQueryString(symbol: String, vararg arguments: String?) =
        formatStringListToQueryStringWithBrackets(arguments.filterNotNull().toList(), prefix = "$symbol(")
}
