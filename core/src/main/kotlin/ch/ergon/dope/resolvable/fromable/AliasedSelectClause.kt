package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectClause

class AliasedSelectClause(private val alias: String, private val selectClause: ISelectClause) : Fromable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val selectClauseDopeQuery = selectClause.toDopeQuery(manager)
        return DopeQuery(
            queryString = "(${selectClauseDopeQuery.queryString}) AS `$alias`",
            parameters = selectClauseDopeQuery.parameters,
            manager = manager,
        )
    }
}
