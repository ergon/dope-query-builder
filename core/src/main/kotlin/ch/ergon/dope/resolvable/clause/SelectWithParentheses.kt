package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.validtype.ValidType

class SelectWithParentheses<T : ValidType>(private val selectClause: ISelectOffsetClause<T>) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val selectClauseDopeQuery = selectClause.toDopeQuery(manager)
        return DopeQuery("(${selectClauseDopeQuery.queryString})", selectClauseDopeQuery.parameters)
    }
}
