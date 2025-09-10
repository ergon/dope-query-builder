package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.clause.SetOperatorType.EXCEPT
import ch.ergon.dope.resolvable.clause.SetOperatorType.INTERSECT
import ch.ergon.dope.resolvable.clause.SetOperatorType.UNION
import ch.ergon.dope.validtype.ValidType

enum class SetOperatorType {
    UNION,
    EXCEPT,
    INTERSECT,
}

data class SetOperator<T : ValidType>(
    val setOperatorType: SetOperatorType,
    val leftSelect: ISelectOffsetClause<T>,
    val rightSelect: ISelectOffsetClause<T>,
    val duplicatesAllowed: Boolean,
) : ISelectOffsetClause<T>

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
