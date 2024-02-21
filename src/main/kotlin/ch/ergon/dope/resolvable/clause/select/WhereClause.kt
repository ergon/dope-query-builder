package ch.ergon.dope.resolvable.clause.select

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.BooleanType

class WhereClause(private val condition: TypeExpression<BooleanType>) : Clause {
    override fun toQueryString(): String = formatToQueryString("WHERE", condition)
}
