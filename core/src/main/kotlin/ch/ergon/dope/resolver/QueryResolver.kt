package ch.ergon.dope.resolver

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable

interface QueryResolver<T : DopeQuery> {
    val manager: DopeQueryManager
    fun resolve(resolvable: Resolvable): T
}
