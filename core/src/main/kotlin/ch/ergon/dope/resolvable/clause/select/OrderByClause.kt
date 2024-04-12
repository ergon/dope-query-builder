package ch.ergon.dope.resolvable.clause.select

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.select.factory.OrderByType
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.StringType

class OrderByClause(private val stringField: Field<StringType>) : Clause {
    override fun toQueryString(): String = formatToQueryString("ORDER BY", stringField)
}

class OrderByTypeClause(private val stringField: Field<StringType>, private val orderByType: OrderByType) : Clause {
    override fun toQueryString(): String = formatToQueryString("ORDER BY", stringField.toQueryString(), orderByType.type)
}
