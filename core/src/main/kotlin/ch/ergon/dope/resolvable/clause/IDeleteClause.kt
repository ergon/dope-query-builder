package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.clause.model.DeleteLimitClause
import ch.ergon.dope.resolvable.clause.model.DeleteOffsetClause
import ch.ergon.dope.resolvable.clause.model.DeleteUseKeysArrayClause
import ch.ergon.dope.resolvable.clause.model.DeleteUseKeysStringClause
import ch.ergon.dope.resolvable.clause.model.DeleteWhereClause
import ch.ergon.dope.resolvable.clause.model.ReturningClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

interface IDeleteReturningClause : Clause

interface IDeleteOffsetClause : IDeleteReturningClause {
    fun returning(field: Field<out ValidType>, vararg fields: Field<out ValidType>) = ReturningClause(field, *fields, parentClause = this)
}

interface IDeleteLimitClause : IDeleteOffsetClause {
    fun offset(numberExpression: TypeExpression<NumberType>) = DeleteOffsetClause(numberExpression, this)
    fun offset(number: Number): DeleteOffsetClause = offset(number.toNumberType())
}

interface IDeleteWhereClause : IDeleteLimitClause {
    fun limit(numberExpression: TypeExpression<NumberType>) = DeleteLimitClause(numberExpression, this)
    fun limit(number: Number): DeleteLimitClause = limit(number.toNumberType())
}

interface IDeleteUseKeysClause : IDeleteWhereClause {
    fun where(booleanExpression: TypeExpression<BooleanType>) = DeleteWhereClause(booleanExpression, this)
}

interface IDeleteClause : IDeleteUseKeysClause {
    fun useKeys(key: TypeExpression<StringType>) = DeleteUseKeysStringClause(key, this)

    @Suppress("INAPPLICABLE_JVM_NAME")
    @JvmName("useKeysArray")
    fun useKeys(key: TypeExpression<ArrayType<StringType>>) = DeleteUseKeysArrayClause(key, this)
}
