package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.ISelectClause
import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.formatMinimumTwoToQueryString
import ch.ergon.dope.resolvable.fromable.Fromable

class FromClause(private val fromable: Fromable, private val parentClause: ISelectClause) : ISelectUnnestClause {
    override fun toQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toQuery()
        val fromableDopeQuery = fromable.toQuery()
        return DopeQuery(
            queryString = formatMinimumTwoToQueryString(parentDopeQuery.queryString, "FROM", fromableDopeQuery.queryString),
            parameters = fromableDopeQuery.parameters + parentDopeQuery.parameters,
        )
    }
}
