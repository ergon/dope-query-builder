package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

interface IDeleteOffsetClass : Clause {
    fun returning(field: Field<out ValidType>, vararg fields: Field<out ValidType>) = ReturningClause(field, *fields, parent = this)
}

interface IDeleteLimitClass : IDeleteOffsetClass {
    fun offset(numberExpression: TypeExpression<NumberType>) = DeleteOffsetClause(numberExpression, this)
}

interface IDeleteWhereClause : IDeleteLimitClass {
    fun limit(numberExpression: TypeExpression<NumberType>) = DeleteLimitClause(numberExpression, this)
}

interface IDeleteClause : IDeleteWhereClause {
    fun where(booleanExpression: TypeExpression<BooleanType>) = DeleteWhereClause(booleanExpression, this)
}
