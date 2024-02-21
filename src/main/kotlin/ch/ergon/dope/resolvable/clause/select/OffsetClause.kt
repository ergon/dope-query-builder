package ch.ergon.dope.resolvable.clause.select

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.NumberType

class OffsetClause(private val numberExpression: TypeExpression<NumberType>) : Clause {
    override fun toQueryString(): String = formatToQueryString("OFFSET", numberExpression)
}
