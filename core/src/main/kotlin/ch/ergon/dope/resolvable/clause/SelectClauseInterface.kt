package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

interface IOffsetClause : Clause

interface ILimitClause : IOffsetClause {
    fun offset(numberExpression: TypeExpression<NumberType>): OffsetClause = OffsetClause(numberExpression, this)
}

enum class OrderByType(val type: String) {
    ASC("ASC"),
    DESC("DESC"),
}

interface IOrderByClause : ILimitClause {
    fun limit(numberExpression: TypeExpression<NumberType>): LimitClause = LimitClause(numberExpression, this)

    fun limit(number: Number): LimitClause = limit(number.toNumberType())
}

interface IGroupByClause : IOrderByClause {
    fun orderBy(stringField: Field<StringType>): OrderByClause = OrderByClause(stringField, this)

    fun orderBy(stringField: Field<StringType>, orderByType: OrderByType): OrderByTypeClause = OrderByTypeClause(stringField, orderByType, this)
}

interface IWhereClause : IGroupByClause {
    fun groupBy(field: Field<out ValidType>, vararg fields: Field<out ValidType>): GroupByClause = GroupByClause(field, *fields, parent = this)
}

interface Fromable : Resolvable

interface IFromClause : IWhereClause {
    fun where(whereExpression: TypeExpression<BooleanType>) = WhereClause(whereExpression, this)
}

interface ISelectClause : IFromClause {
    fun from(fromable: Fromable) = FromClause(fromable, this)
}
