package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

interface IDOffsetClass : Clause {
    fun returning(field: Field<out ValidType>, vararg fields: Field<out ValidType>) = ReturningClause(field, *fields, parent = this)
}

interface IDLimitClass : IDOffsetClass {
    fun offset(numberExpression: TypeExpression<NumberType>) = DOffsetClause(numberExpression, this)
}

interface IDWhereClause : IDLimitClass {
    fun limit(numberExpression: TypeExpression<NumberType>) = DLimitClause(numberExpression, this)
}

interface IDeleteClause : IDWhereClause {
    fun where(booleanExpression: TypeExpression<BooleanType>) = DWhereClause(booleanExpression, this)
}

class ReturningClause(
    private val field: Field<out ValidType>,
    private vararg val fields: Field<out ValidType>,
    private val parent: IDOffsetClass,
) : Clause {
    override fun toQueryString(): String = formatToQueryString(parent, "RETURNING", field, *fields)
}

class DOffsetClause(private val numberExpression: TypeExpression<NumberType>, private val parent: IDLimitClass) : IDOffsetClass {
    override fun toQueryString(): String = formatToQueryString(parent, "OFFSET", numberExpression)
}

class DLimitClause(private val numberExpression: TypeExpression<NumberType>, private val parent: IDWhereClause) : IDLimitClass {
    override fun toQueryString(): String = formatToQueryString(parent, "LIMIT", numberExpression)
}

class DWhereClause(private val booleanExpression: TypeExpression<BooleanType>, private val parent: IDeleteClause) : IDWhereClause {
    override fun toQueryString(): String = formatToQueryString(parent, "WHERE", booleanExpression)
}

class DeleteClause(private val from: Bucket) : IDeleteClause {
    override fun toQueryString(): String = "DELETE FROM ${from.toQueryString()}"
}
