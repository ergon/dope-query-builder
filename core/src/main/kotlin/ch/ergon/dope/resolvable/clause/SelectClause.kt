package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

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
