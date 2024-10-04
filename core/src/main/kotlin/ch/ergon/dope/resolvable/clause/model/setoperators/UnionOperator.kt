package ch.ergon.dope.resolvable.clause.model.setoperators

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause

class UnionOperator(
    leftSelect: ISelectOffsetClause,
    rightSelect: ISelectOffsetClause,
    duplicatesAllowed: Boolean = false,
) : SetOperator("UNION", leftSelect, rightSelect, duplicatesAllowed)

fun ISelectOffsetClause.union(select: ISelectOffsetClause) = UnionOperator(this, select)

fun ISelectOffsetClause.unionAll(select: ISelectOffsetClause) = UnionOperator(this, select, true)
