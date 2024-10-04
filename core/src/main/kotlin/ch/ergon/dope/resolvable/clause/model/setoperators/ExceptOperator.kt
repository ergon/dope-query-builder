package ch.ergon.dope.resolvable.clause.model.setoperators

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause

class ExceptOperator(
    leftSelect: ISelectOffsetClause,
    rightSelect: ISelectOffsetClause,
    duplicatesAllowed: Boolean = false,
) : SetOperator("EXCEPT", leftSelect, rightSelect, duplicatesAllowed)

fun ISelectOffsetClause.except(select: ISelectOffsetClause) = ExceptOperator(this, select)

fun ISelectOffsetClause.exceptAll(select: ISelectOffsetClause) = ExceptOperator(this, select, true)
