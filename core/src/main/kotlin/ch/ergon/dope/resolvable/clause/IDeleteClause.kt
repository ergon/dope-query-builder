package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.clause.model.DeleteLimitClause
import ch.ergon.dope.resolvable.clause.model.DeleteOffsetClause
import ch.ergon.dope.resolvable.clause.model.DeleteReturningClause
import ch.ergon.dope.resolvable.clause.model.DeleteReturningSingleClause
import ch.ergon.dope.resolvable.clause.model.DeleteWhereClause
import ch.ergon.dope.resolvable.clause.model.ReturningType.ELEMENT
import ch.ergon.dope.resolvable.clause.model.ReturningType.RAW
import ch.ergon.dope.resolvable.clause.model.ReturningType.VALUE
import ch.ergon.dope.resolvable.expression.AsteriskExpression
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.Returnable
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

interface IDeleteReturningClause : Clause

interface IDeleteOffsetClause : IDeleteReturningClause {
    fun returning(returningExpression: Returnable, vararg additionalReturningExpressions: Returnable) =
        DeleteReturningClause(returningExpression, *additionalReturningExpressions, parentClause = this)
    fun returningAsterisk(bucket: Bucket? = null) = DeleteReturningClause(AsteriskExpression(bucket), parentClause = this)

    fun returningRaw(returningExpression: TypeExpression<out ValidType>) =
        DeleteReturningSingleClause(returningExpression, returningType = RAW, parentClause = this)
    fun returningValue(returningExpression: TypeExpression<out ValidType>) =
        DeleteReturningSingleClause(returningExpression, returningType = VALUE, parentClause = this)
    fun returningElement(returningExpression: TypeExpression<out ValidType>) =
        DeleteReturningSingleClause(returningExpression, returningType = ELEMENT, parentClause = this)
}

interface IDeleteLimitClause : IDeleteOffsetClause {
    fun offset(numberExpression: TypeExpression<NumberType>) = DeleteOffsetClause(numberExpression, this)
    fun offset(number: Number) = offset(number.toDopeType())
}

interface IDeleteWhereClause : IDeleteLimitClause {
    fun limit(numberExpression: TypeExpression<NumberType>) = DeleteLimitClause(numberExpression, this)
    fun limit(number: Number) = limit(number.toDopeType())
}

interface IDeleteClause : IDeleteWhereClause {
    fun where(booleanExpression: TypeExpression<BooleanType>) = DeleteWhereClause(booleanExpression, this)
}
