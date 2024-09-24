package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectJoinClause

class AliasedSelectClause(private val alias: String, private val parentClause: ISelectJoinClause) : Fromable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentClauseDopeQuery = parentClause.toDopeQuery(manager)
        return DopeQuery(
            queryString = "(${parentClauseDopeQuery.queryString}) AS `$alias`",
            parameters = parentClauseDopeQuery.parameters,
        )
    }
}
