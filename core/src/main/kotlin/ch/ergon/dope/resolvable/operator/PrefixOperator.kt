package ch.ergon.dope.resolvable.operator

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.resolvable.formatToQueryStringWithBrackets

open class PrefixOperator(private val symbol: String, private val argument: Resolvable) {
    fun toPrefixQueryString(separator: String) = formatToQueryString(symbol, separator = separator, argument)
}
