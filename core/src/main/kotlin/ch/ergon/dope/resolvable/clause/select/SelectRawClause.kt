package ch.ergon.dope.resolvable.clause.select

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.resolvable.formatToQueryString

class SelectRawClause(private val expression: SingleExpression) : Clause {
    override fun toQueryString(): String = formatToQueryString("SELECT RAW", expression)
}
