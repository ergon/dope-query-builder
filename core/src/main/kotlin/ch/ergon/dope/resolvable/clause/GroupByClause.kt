package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.ValidType

class GroupByClause(
    private val field: Field<out ValidType>,
    private vararg val fields: Field<out ValidType>,
    private val parentClause: IWhereClause,
) : IGroupByClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, "GROUP BY", field, *fields)
}
