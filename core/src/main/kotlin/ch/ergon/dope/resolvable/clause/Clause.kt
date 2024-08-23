package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable

interface Clause : Resolvable {
    fun build(): DopeQuery {
        // ParameterManager.resetCounter()
        // IteratorManager.resetCounter()
        return toDopeQuery(manager = DopeQueryManager())
    }
}
