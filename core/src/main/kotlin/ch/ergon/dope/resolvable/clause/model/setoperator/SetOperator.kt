package ch.ergon.dope.resolvable.clause.model.setoperator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.clause.model.setoperator.SetOperatorType.EXCEPT
import ch.ergon.dope.resolvable.clause.model.setoperator.SetOperatorType.INTERSECT
import ch.ergon.dope.resolvable.clause.model.setoperator.SetOperatorType.UNION

enum class SetOperatorType {
    UNION,
    EXCEPT,
    INTERSECT,
}

class SetOperator(
    private val setOperatorType: SetOperatorType,
    private val leftSelect: ISelectOffsetClause,
    private val rightSelect: ISelectOffsetClause,
    private val duplicatesAllowed: Boolean,
) : ISelectOffsetClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val leftSelectDopeQuery = leftSelect.toDopeQuery(manager)
        val rightSelectDopeQuery = rightSelect.toDopeQuery(manager)
        return DopeQuery(
            queryString = "(${leftSelectDopeQuery.queryString}) $setOperatorType " +
                (if (duplicatesAllowed) "ALL " else "") +
                "(${rightSelectDopeQuery.queryString})",
            parameters = leftSelectDopeQuery.parameters.merge(rightSelectDopeQuery.parameters),
        )
    }
}

fun ISelectOffsetClause.union(select: ISelectOffsetClause) =
    SetOperator(UNION, this, select, duplicatesAllowed = false)

fun ISelectOffsetClause.unionAll(select: ISelectOffsetClause) =
    SetOperator(UNION, this, select, duplicatesAllowed = true)

fun ISelectOffsetClause.intersect(select: ISelectOffsetClause) =
    SetOperator(INTERSECT, this, select, duplicatesAllowed = false)

fun ISelectOffsetClause.intersectAll(select: ISelectOffsetClause) =
    SetOperator(INTERSECT, this, select, duplicatesAllowed = true)

fun ISelectOffsetClause.except(select: ISelectOffsetClause) =
    SetOperator(EXCEPT, this, select, duplicatesAllowed = false)

fun ISelectOffsetClause.exceptAll(select: ISelectOffsetClause) =
    SetOperator(EXCEPT, this, select, duplicatesAllowed = true)
