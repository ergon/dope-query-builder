package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class SelectExpression<T : ValidType>(private val selectClause: ISelectOffsetClause<T>) : TypeExpression<ArrayType<T>> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val selectClauseDopeQuery = selectClause.toDopeQuery(manager)
        return DopeQuery("(${selectClauseDopeQuery.queryString})", selectClauseDopeQuery.parameters)
    }
}
