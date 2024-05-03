package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.ISelectClause
import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.resolvable.fromable.Fromable

class FromClause(private val fromable: Fromable, private val parentClause: ISelectClause) : ISelectUnnestClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, "FROM", fromable)
}
