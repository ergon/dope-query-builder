package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.validtype.ValidType

class AliasedSelectClause<R : ValidType>(
    private val alias: String,
    private val parentClause: ISelectOffsetClause<R>,
) : Fromable, Joinable, Expression {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentClauseDopeQuery = parentClause.toDopeQuery(manager)
        return DopeQuery(
            queryString = "(${parentClauseDopeQuery.queryString}) AS `$alias`",
            parameters = parentClauseDopeQuery.parameters,
        )
    }
}
