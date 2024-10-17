package ch.ergon.dope.resolvable.clause.model.setoperator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.clause.model.setoperator.SetOperatorType.EXCEPT
import ch.ergon.dope.resolvable.clause.model.setoperator.SetOperatorType.INTERSECT
import ch.ergon.dope.resolvable.clause.model.setoperator.SetOperatorType.UNION
import ch.ergon.dope.validtype.ValidType

enum class SetOperatorType {
    UNION,
    EXCEPT,
    INTERSECT,
}

class SetOperator<T : ValidType>(
    private val setOperatorType: SetOperatorType,
    private val leftSelect: ISelectOffsetClause<T>,
    private val rightSelect: ISelectOffsetClause<T>,
    private val duplicatesAllowed: Boolean,
) : ISelectOffsetClause<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val leftSelectDopeQuery = leftSelect.toDopeQuery(manager)
        val rightSelectDopeQuery = rightSelect.toDopeQuery(manager)
        return DopeQuery(
            queryString = "(${leftSelectDopeQuery.queryString}) $setOperatorType " +
                (if (duplicatesAllowed) "ALL " else "") +
                "(${rightSelectDopeQuery.queryString})",
            parameters = leftSelectDopeQuery.parameters + rightSelectDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> ISelectOffsetClause<T>.union(select: ISelectOffsetClause<T>) =
    SetOperator(UNION, this, select, duplicatesAllowed = false)

fun <T : ValidType> ISelectOffsetClause<T>.unionAll(select: ISelectOffsetClause<T>) =
    SetOperator(UNION, this, select, duplicatesAllowed = true)

fun <T : ValidType> ISelectOffsetClause<T>.intersect(select: ISelectOffsetClause<T>) =
    SetOperator(INTERSECT, this, select, duplicatesAllowed = false)

fun <T : ValidType> ISelectOffsetClause<T>.intersectAll(select: ISelectOffsetClause<T>) =
    SetOperator(INTERSECT, this, select, duplicatesAllowed = true)

fun <T : ValidType> ISelectOffsetClause<T>.except(select: ISelectOffsetClause<T>) =
    SetOperator(EXCEPT, this, select, duplicatesAllowed = false)

fun <T : ValidType> ISelectOffsetClause<T>.exceptAll(select: ISelectOffsetClause<T>) =
    SetOperator(EXCEPT, this, select, duplicatesAllowed = true)
