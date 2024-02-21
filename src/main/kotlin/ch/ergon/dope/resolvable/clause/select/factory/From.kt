package ch.ergon.dope.resolvable.clause.select.factory

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.ClauseBuilder
import ch.ergon.dope.resolvable.clause.select.Fromable

open class From(query: List<Clause>) : Where(query) {
    fun from(fromable: Fromable): Where = ClauseBuilder(clauses).from(fromable)
}
