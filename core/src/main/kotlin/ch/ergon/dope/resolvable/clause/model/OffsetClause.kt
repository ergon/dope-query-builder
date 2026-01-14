package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.IDeleteLimitClause
import ch.ergon.dope.resolvable.clause.IDeleteOffsetClause
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

sealed class OffsetClause(
    open val numberExpression: TypeExpression<NumberType>,
    open val parentClause: Clause,
) : Clause

data class SelectOffsetClause<T : ValidType>(
    override val numberExpression: TypeExpression<NumberType>,
    override val parentClause: ISelectLimitClause<T>,
) : ISelectOffsetClause<T>, OffsetClause(numberExpression, parentClause)

data class DeleteOffsetClause(override val numberExpression: TypeExpression<NumberType>, override val parentClause: IDeleteLimitClause) :
    IDeleteOffsetClause, OffsetClause(numberExpression, parentClause)
