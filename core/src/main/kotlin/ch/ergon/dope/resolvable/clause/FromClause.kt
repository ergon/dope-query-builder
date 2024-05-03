package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.resolvable.fromable.Fromable

class FromClause(private val fromable: Fromable, private val parentClause: ISelectClause) : IJoinClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, "FROM", fromable)
}
