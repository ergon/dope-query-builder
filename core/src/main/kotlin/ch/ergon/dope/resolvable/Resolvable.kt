package ch.ergon.dope.resolvable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager

interface Resolvable {
    fun toDopeQuery(manager: DopeQueryManager): DopeQuery
}
