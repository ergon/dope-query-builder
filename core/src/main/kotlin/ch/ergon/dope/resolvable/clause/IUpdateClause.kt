package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.clause.model.SetClause
import ch.ergon.dope.resolvable.clause.model.UnsetClause
import ch.ergon.dope.resolvable.clause.model.UpdateLimitClause
import ch.ergon.dope.resolvable.clause.model.UpdateReturningClause
import ch.ergon.dope.resolvable.clause.model.UpdateWhereClause
import ch.ergon.dope.resolvable.clause.model.to
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

interface IUpdateReturningClause : Clause

interface IUpdateLimitClause : IUpdateReturningClause {
    fun returning(field: Field<out ValidType>, vararg fields: Field<out ValidType>) =
        UpdateReturningClause(field, *fields, parentClause = this)
}

interface IUpdateWhereClause : IUpdateLimitClause {
    fun limit(numberExpression: TypeExpression<NumberType>) = UpdateLimitClause(numberExpression, this)
    fun limit(numberExpression: Number) = UpdateLimitClause(numberExpression.toDopeType(), this)
}

interface IUpdateUnsetClause : IUpdateWhereClause {
    fun where(booleanExpression: TypeExpression<BooleanType>) = UpdateWhereClause(booleanExpression, this)
}

interface IUpdateSetClause : IUpdateUnsetClause {
    fun unset(field: Field<out ValidType>) = UnsetClause(field, parentClause = this)
}

interface IUpdateClause : IUpdateSetClause {
    fun <T : ValidType> set(field: Field<T>, value: TypeExpression<T>) = SetClause(field.to(value), parentClause = this)
    fun set(field: Field<NumberType>, value: Number) = SetClause(field.to(value.toDopeType()), parentClause = this)
    fun set(field: Field<StringType>, value: String) = SetClause(field.to(value.toDopeType()), parentClause = this)
    fun set(field: Field<BooleanType>, value: Boolean) = SetClause(field.to(value.toDopeType()), parentClause = this)
}
