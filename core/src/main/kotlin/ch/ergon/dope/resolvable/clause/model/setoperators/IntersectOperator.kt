package ch.ergon.dope.resolvable.clause.model.setoperators

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause

class IntersectOperator(
    leftSelect: ISelectOffsetClause,
    rightSelect: ISelectOffsetClause,
    duplicatesAllowed: Boolean = false,
) : SetOperator("INTERSECT", leftSelect, rightSelect, duplicatesAllowed)

fun ISelectOffsetClause.intersect(select: ISelectOffsetClause) = IntersectOperator(this, select)

fun ISelectOffsetClause.intersectAll(select: ISelectOffsetClause) = IntersectOperator(this, select, true)
