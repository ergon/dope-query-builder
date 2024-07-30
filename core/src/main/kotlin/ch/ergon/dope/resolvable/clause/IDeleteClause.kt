package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.clause.model.DeleteLimitClause
import ch.ergon.dope.resolvable.clause.model.DeleteOffsetClause
import ch.ergon.dope.resolvable.clause.model.DeleteReturningClause
import ch.ergon.dope.resolvable.clause.model.DeleteUseKeysClause.Companion.DeleteUseKeysClause
import ch.ergon.dope.resolvable.clause.model.DeleteWhereClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

interface IDeleteReturningClause : Clause

interface IDeleteOffsetClause : IDeleteReturningClause {
    fun returning(field: Field<out ValidType>, vararg fields: Field<out ValidType>) = DeleteReturningClause(field, *fields, parentClause = this)
}

interface IDeleteLimitClause : IDeleteOffsetClause {
    fun offset(numberExpression: TypeExpression<NumberType>) = DeleteOffsetClause(numberExpression, this)
    fun offset(number: Number) = offset(number.toDopeType())
}

interface IDeleteWhereClause : IDeleteLimitClause {
    fun limit(numberExpression: TypeExpression<NumberType>) = DeleteLimitClause(numberExpression, this)
    fun limit(number: Number) = limit(number.toDopeType())
}

interface IDeleteUseKeysClause : IDeleteWhereClause {
    fun where(booleanExpression: TypeExpression<BooleanType>) = DeleteWhereClause(booleanExpression, this)
}

interface IDeleteClause : IDeleteUseKeysClause {
    fun useKeys(key: TypeExpression<StringType>) = DeleteUseKeysClause(key, this)

    // JvmName annotation in interfaces is currently not supported. https://youtrack.jetbrains.com/issue/KT-20068
    @Suppress("INAPPLICABLE_JVM_NAME")
    @JvmName("useKeysArray")
    fun useKeys(key: TypeExpression<ArrayType<StringType>>) = DeleteUseKeysClause(key, this)
}
