package ch.ergon.dope.resolvable.operator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.formatToQueryStringWithBrackets

open class PrefixOperator(private val symbol: String, private val argument: Resolvable) {
    fun toPrefixQueryString(): DopeQuery {
        val argumentDopeQuery = argument.toQuery()
        return DopeQuery(
            queryString = formatToQueryStringWithBrackets(symbol, argumentDopeQuery.queryString),
            parameters = argumentDopeQuery.parameters,
        )
    }
}
