package ch.ergon.dope.resolvable.operator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.formatToQueryStringWithSeparator

open class PrefixOperator(private val symbol: String, private val argument: Resolvable) {
    fun toPrefixDopeQuery(separator: String): DopeQuery {
        val argumentDopeQuery = argument.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryStringWithSeparator(symbol, separator = separator, argumentDopeQuery.queryString),
            parameters = argumentDopeQuery.parameters,
        )
    }
}
