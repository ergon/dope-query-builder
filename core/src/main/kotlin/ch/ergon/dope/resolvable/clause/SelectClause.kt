package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.formatToQueryString
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

class OffsetClause(private val numberExpression: TypeExpression<NumberType>, private val parent: ILimitClause) : IOffsetClause {
    override fun toQueryString(): String = formatToQueryString(parent, "OFFSET", numberExpression)
}

class LimitClause(private val numberExpression: TypeExpression<NumberType>, private val parent: IOrderByClause) : ILimitClause {
    override fun toQueryString(): String = formatToQueryString(parent, "LIMIT", numberExpression)
}

open class OrderByClause(private val stringField: Field<StringType>, private val parent: IGroupByClause) : IOrderByClause {
    override fun toQueryString(): String = formatToQueryString(parent, "ORDER BY", stringField)
}

class OrderByTypeClause(
    stringField: Field<StringType>,
    private val orderByType: OrderByType,
    parent: IGroupByClause,
) : OrderByClause(stringField, parent) {
    override fun toQueryString(): String = super.toQueryString() + " ${orderByType.type}"
}

class GroupByClause(
    private val field: Field<out ValidType>,
    private vararg val fields: Field<out ValidType>,
    private val parent: IWhereClause,
) : IGroupByClause {
    override fun toQueryString(): String = formatToQueryString(parent, "GROUP BY", field, *fields)
}

class WhereClause(private val whereExpression: TypeExpression<BooleanType>, private val parent: IFromClause) : IWhereClause {
    override fun toQueryString(): String = formatToQueryString(parent, "WHERE", whereExpression)
}

class FromClause(private val fromable: Fromable, private val parent: ISelectClause) : IFromClause {
    override fun toQueryString(): String = formatToQueryString(parent, "FROM", fromable)
}

class SelectRawClause(private val expression: Expression) : ISelectClause {
    override fun toQueryString(): String = formatToQueryString("SELECT RAW", expression)
}

class SelectDistinctClause(private val expression: Expression, private vararg val expressions: Expression) : ISelectClause {
    override fun toQueryString(): String = formatToQueryString("SELECT DISTINCT", expression, *expressions)
}

class SelectClause(private val expression: Expression, private vararg val expressions: Expression) : ISelectClause {
    override fun toQueryString(): String = formatToQueryString("SELECT", expression, *expressions)
}
