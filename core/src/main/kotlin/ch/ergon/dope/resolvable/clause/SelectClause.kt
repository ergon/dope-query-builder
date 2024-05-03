package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.resolvable.fromable.Fromable
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class OffsetClause(private val numberExpression: TypeExpression<NumberType>, private val parentClause: ILimitClause) : IOffsetClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, "OFFSET", numberExpression)
}

class LimitClause(private val numberExpression: TypeExpression<NumberType>, private val parentClause: IOrderByClause) : ILimitClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, "LIMIT", numberExpression)
}

open class OrderByClause(private val stringField: Field<StringType>, private val parentClause: IGroupByClause) : IOrderByClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, "ORDER BY", stringField)
}

class OrderByTypeClause(
    stringField: Field<StringType>,
    private val orderByType: OrderByType,
    parentClause: IGroupByClause,
) : OrderByClause(stringField, parentClause) {
    override fun toQueryString(): String = super.toQueryString() + " ${orderByType.type}"
}

class GroupByClause(
    private val field: Field<out ValidType>,
    private vararg val fields: Field<out ValidType>,
    private val parentClause: IWhereClause,
) : IGroupByClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, "GROUP BY", field, *fields)
}

class WhereClause(private val whereExpression: TypeExpression<BooleanType>, private val parentClause: IFromClause) : IWhereClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, "WHERE", whereExpression)
}

class FromClause(private val fromable: Fromable, private val parentClause: ISelectClause) : IJoinClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, "FROM", fromable)
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
