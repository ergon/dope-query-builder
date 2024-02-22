package ch.ergon.dope.resolvable.clause.select

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.formatToQueryString

class SelectRawClause(private val expression: Expression) : Clause {
    override fun toQueryString(): String = formatToQueryString("SELECT RAW", expression)
}
