package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.build.QueryResolver
import ch.ergon.dope.resolvable.Resolvable

interface Clause : Resolvable {
    fun <T : DopeQuery> build(resolver: QueryResolver<T>): T = toDopeQuery(DopeQueryManager(resolver))

    @Deprecated("Use build(resolver) and pass a backend-specific resolver")
    fun build(): Nothing = throw UnsupportedOperationException("build() without resolver is removed. Use build(resolver).")
}
