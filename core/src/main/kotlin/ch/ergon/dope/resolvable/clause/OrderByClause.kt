package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.StringType

enum class OrderByType(val type: String) {
    ASC("ASC"),
    DESC("DESC"),
}

open class OrderByClause(private val stringField: Field<StringType>, private val parentClause: IGroupByClause) :
    IOrderByClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, "ORDER BY", stringField)
}

class OrderByTypeClause(
    stringField: Field<StringType>,
    private val orderByType: OrderByType,
    parentClause: IGroupByClause,
) : OrderByClause(stringField, parentClause) {
    override fun toQueryString(): String = super.toQueryString() + " ${orderByType.type}"
}
