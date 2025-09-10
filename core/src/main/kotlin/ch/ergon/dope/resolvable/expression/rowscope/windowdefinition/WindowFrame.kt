package ch.ergon.dope.resolvable.expression.rowscope.windowdefinition

import ch.ergon.dope.resolvable.Resolvable

private const val EXCLUDE = "EXCLUDE"

enum class WindowFrameExclusion(val queryString: String) {
    EXCLUDE_CURRENT_ROW("$EXCLUDE CURRENT ROW"),
    EXCLUDE_GROUP("$EXCLUDE GROUP"),
    EXCLUDE_TIES("$EXCLUDE TIES"),
    EXCLUDE_NO_OTHERS("$EXCLUDE NO OTHERS"),
}

enum class WindowFrameType(val queryString: String) {
    ROWS("ROWS"),
    RANGE("RANGE"),
    GROUPS("GROUPS"),
}

data class WindowFrameClause(
    val windowFrameType: WindowFrameType,
    val windowFrameExtent: WindowFrameExtent,
    val windowFrameExclusion: WindowFrameExclusion? = null,
) : Resolvable
