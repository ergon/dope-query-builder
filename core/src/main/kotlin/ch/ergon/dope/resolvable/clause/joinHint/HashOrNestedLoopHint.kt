package ch.ergon.dope.resolvable.clause.joinHint

import ch.ergon.dope.resolvable.Resolvable

enum class HashOrNestedLoopHint : Resolvable {
    HASH_BUILD,
    HASH_PROBE,
    NESTED_LOOP,
}
