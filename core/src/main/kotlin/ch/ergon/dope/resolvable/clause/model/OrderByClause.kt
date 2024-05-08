package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.StringType

enum class OrderByType(val type: String) {
    ASC("ASC"),
    DESC("DESC"),
}

open class SelectOrderByClause(private val stringField: Field<StringType>, private val parentClause: ISelectGroupByClause) :
    ISelectOrderByClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, "ORDER BY", stringField)
}

class SelectOrderByTypeClause(
    stringField: Field<StringType>,
    private val orderByType: OrderByType,
    parentClause: ISelectGroupByClause,
) : SelectOrderByClause(stringField, parentClause) {
    override fun toQueryString(): String = super.toQueryString() + " ${orderByType.type}"
}