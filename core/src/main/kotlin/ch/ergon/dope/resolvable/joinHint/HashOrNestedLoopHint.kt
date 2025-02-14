package ch.ergon.dope.resolvable.joinHint

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable

enum class HashOrNestedLoopHint : Resolvable {
    HASH_BUILD {
        override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(
            queryString = "HASH (BUILD)",
        )
    },
    HASH_PROBE {
        override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(
            queryString = "HASH (PROBE)",
        )
    },
    NESTED_LOOP {
        override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(
            queryString = "NL",
        )
    },
}
