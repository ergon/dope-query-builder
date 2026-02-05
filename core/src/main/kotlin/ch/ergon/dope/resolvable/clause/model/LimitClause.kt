package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.IDeleteLimitClause
import ch.ergon.dope.resolvable.clause.IDeleteWhereClause
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.clause.IUpdateLimitClause
import ch.ergon.dope.resolvable.clause.IUpdateWhereClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

sealed interface LimitClause : Clause {
    val numberExpression: TypeExpression<NumberType>
    val parentClause: Clause
}

data class SelectLimitClause<T : ValidType>(
    override val numberExpression: TypeExpression<NumberType>,
    override val parentClause: ISelectOrderByClause<T>,
) : ISelectLimitClause<T>, LimitClause

data class DeleteLimitClause(override val numberExpression: TypeExpression<NumberType>, override val parentClause: IDeleteWhereClause) :
    IDeleteLimitClause, LimitClause

data class UpdateLimitClause(override val numberExpression: TypeExpression<NumberType>, override val parentClause: IUpdateWhereClause) :
    IUpdateLimitClause, LimitClause
