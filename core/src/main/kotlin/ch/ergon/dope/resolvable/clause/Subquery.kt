package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.validtype.ValidType

class Subquery<R : ValidType>(private val iSelectOffsetClause: ISelectOffsetClause<R>) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val iSelectOffsetClauseDopeQuery = iSelectOffsetClause.toDopeQuery(manager)
        return DopeQuery("(${iSelectOffsetClauseDopeQuery.queryString})", iSelectOffsetClauseDopeQuery.parameters)
    }
}
