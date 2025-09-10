package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.clause.IDeleteWhereClause
import ch.ergon.dope.resolvable.clause.ISelectLetClause
import ch.ergon.dope.resolvable.clause.ISelectWhereClause
import ch.ergon.dope.resolvable.clause.IUpdateUnsetClause
import ch.ergon.dope.resolvable.clause.IUpdateWhereClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

sealed interface WhereClause {
    val whereExpression: TypeExpression<BooleanType>
    val parentClause: Clause
}

data class SelectWhereClause<T : ValidType>(
    override val whereExpression: TypeExpression<BooleanType>,
    override val parentClause: ISelectLetClause<T>,
) : ISelectWhereClause<T>, WhereClause

data class DeleteWhereClause(
    override val whereExpression: TypeExpression<BooleanType>,
    override val parentClause: IDeleteClause,
) : IDeleteWhereClause, WhereClause

data class UpdateWhereClause(
    override val whereExpression: TypeExpression<BooleanType>,
    override val parentClause: IUpdateUnsetClause,
) : IUpdateWhereClause, WhereClause
