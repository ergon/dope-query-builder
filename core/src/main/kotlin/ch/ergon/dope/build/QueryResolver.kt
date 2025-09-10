package ch.ergon.dope.build

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable

interface QueryResolver<T : DopeQuery> {
    fun resolve(manager: DopeQueryManager<T>, resolvable: Resolvable): T
}
