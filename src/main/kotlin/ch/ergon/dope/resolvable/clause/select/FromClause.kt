package ch.ergon.dope.resolvable.clause.select

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.formatToQueryString

interface Fromable : Resolvable

class FromClause(private val fromable: Fromable) : Clause {
    override fun toQueryString(): String = formatToQueryString("FROM", fromable.toQueryString())
}
