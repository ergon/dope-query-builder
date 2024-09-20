package ch.ergon.dope.resolvable.clause.model.joinHint

enum class UseHashOrNestedLoopHint(val queryString: String) {
    HASH_BUILD("HASH (BUILD)"),
    HASH_PROBE("HASH (PROBE)"),
    NESTED_LOOP("NL"),
}
