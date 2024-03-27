package ch.ergon.dope.resolvable.clause.select.factory

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.expression.unaliased.type.resetCounter

open class Build(val clauses: List<Clause>) {
    fun build(): String {
        resetCounter()

        val clausesStringList = clauses.map { it.toQueryString() }

        return clausesStringList.joinToString(prefix = "", postfix = "", separator = " ")
    }

// TODO DOPE-170
//    fun asSubquery(name: String) = Subquery(name, query)
}
