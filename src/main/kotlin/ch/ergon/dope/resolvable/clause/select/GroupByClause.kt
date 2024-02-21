package ch.ergon.dope.resolvable.clause.select

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.ValidType

class GroupByClause(private val field: Field<out ValidType>, private vararg val fields: Field<out ValidType>) : Clause {
    override fun toQueryString(): String = formatToQueryString("GROUP BY", field, *fields)
}
