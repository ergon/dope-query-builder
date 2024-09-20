package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.validtype.ValidType

class Subquery<T : ValidType>(private val iSelectOffsetClause: ISelectOffsetClause<T>) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val iSelectOffsetClauseDopeQuery = iSelectOffsetClause.toDopeQuery(manager)
        return DopeQuery("(${iSelectOffsetClauseDopeQuery.queryString})", iSelectOffsetClauseDopeQuery.parameters)
    }
}
