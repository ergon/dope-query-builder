package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.Returnable
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.IDeleteOffsetClause
import ch.ergon.dope.resolvable.clause.IDeleteReturningClause
import ch.ergon.dope.resolvable.clause.IUpdateLimitClause
import ch.ergon.dope.resolvable.clause.IUpdateReturningClause
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.validtype.ValidType

enum class ReturningType {
    ELEMENT,
    RAW,
    VALUE,
}

sealed interface ReturningClause : Resolvable {
    val returnable: Returnable
    val additionalReturnables: List<Returnable>
    val parentClause: Clause
}

data class DeleteReturningClause(
    override val returnable: Returnable,
    override val additionalReturnables: List<Returnable> = emptyList(),
    override val parentClause: IDeleteOffsetClause,
) : IDeleteReturningClause, ReturningClause

data class UpdateReturningClause(
    override val returnable: Returnable,
    override val additionalReturnables: List<Returnable> = emptyList(),
    override val parentClause: IUpdateLimitClause,
) : IUpdateReturningClause, ReturningClause

sealed interface ReturningSingleClause : Resolvable {
    val singleReturnable: SingleExpression<out ValidType>
    val returningType: ReturningType
    val parentClause: Clause
}

data class DeleteReturningSingleClause(
    override val singleReturnable: SingleExpression<out ValidType>,
    override val returningType: ReturningType,
    override val parentClause: IDeleteOffsetClause,
) : IDeleteReturningClause, ReturningSingleClause

data class UpdateReturningSingleClause(
    override val singleReturnable: SingleExpression<out ValidType>,
    override val returningType: ReturningType,
    override val parentClause: IUpdateLimitClause,
) : IUpdateReturningClause, ReturningSingleClause
