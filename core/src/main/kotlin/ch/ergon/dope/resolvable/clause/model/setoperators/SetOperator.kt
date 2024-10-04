package ch.ergon.dope.resolvable.clause.model.setoperators

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause

sealed class SetOperator(
    private val symbol: String,
    private val leftSelect: ISelectOffsetClause,
    private val rightSelect: ISelectOffsetClause,
    private val duplicatesAllowed: Boolean = false,
) : ISelectOffsetClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val leftSelectDopeQuery = leftSelect.toDopeQuery(manager)
        val rightSelectDopeQuery = rightSelect.toDopeQuery(manager)
        return DopeQuery(
            queryString = "(${leftSelectDopeQuery.queryString}) $symbol " +
                (if (duplicatesAllowed) "ALL " else "") +
                "(${rightSelectDopeQuery.queryString})",
            parameters = leftSelectDopeQuery.parameters + rightSelectDopeQuery.parameters,
        )
    }
}
