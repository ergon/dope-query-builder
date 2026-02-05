package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolver.QueryResolver

interface Clause : Resolvable {
    fun <T : DopeQuery> build(resolver: QueryResolver<T>): T = toDopeQuery(resolver)
}
