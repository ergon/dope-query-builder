package ch.ergon.dope.resolvable.clause.model.joinHint

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable

enum class HashOrNestedLoopHint : Resolvable {
    HASH_BUILD {
        override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(
            "HASH (BUILD)",
            emptyMap(),
        )
    },
    HASH_PROBE {
        override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(
            "HASH (PROBE)",
            emptyMap(),
        )
    },
    NESTED_LOOP {
        override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(
            "NL",
            emptyMap(),
        )
    },
}
