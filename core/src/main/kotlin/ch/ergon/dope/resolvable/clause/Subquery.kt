package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.validtype.ValidType

class Subquery<T : ValidType>(private val subClause: ISelectOffsetClause<T>) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val subClauseDopeQuery = subClause.toDopeQuery(manager)
        return DopeQuery("(${subClauseDopeQuery.queryString})", subClauseDopeQuery.parameters)
    }
}
