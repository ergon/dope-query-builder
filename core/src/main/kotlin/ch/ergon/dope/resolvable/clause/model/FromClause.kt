package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectClause
import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.resolvable.fromable.Fromable

class FromClause(private val fromable: Fromable, private val parentClause: ISelectClause) : ISelectUnnestClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val fromableDopeQuery = fromable.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, "FROM", fromableDopeQuery.queryString),
            parameters = fromableDopeQuery.parameters + parentDopeQuery.parameters,
            manager = manager,
        )
    }
}
