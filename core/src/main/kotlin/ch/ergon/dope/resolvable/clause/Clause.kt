package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable

interface Clause : Resolvable {
    fun build(): DopeQuery {
        return toDopeQuery(manager = DopeQueryManager())
    }
}
