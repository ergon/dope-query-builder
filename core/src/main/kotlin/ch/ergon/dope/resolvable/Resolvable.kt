package ch.ergon.dope.resolvable

import ch.ergon.dope.DopeQuery

interface Resolvable {
    fun toDopeQuery(): DopeQuery
}
