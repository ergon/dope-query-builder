package ch.ergon.dope.resolvable.expression.rowscope.windowdefinition

import ch.ergon.dope.resolvable.Resolvable

enum class WindowFrameExclusion {
    EXCLUDE_CURRENT_ROW,
    EXCLUDE_GROUP,
    EXCLUDE_TIES,
    EXCLUDE_NO_OTHERS,
}

enum class WindowFrameType {
    ROWS,
    RANGE,
    GROUPS,
}

data class WindowFrameClause(
    val windowFrameType: WindowFrameType,
    val windowFrameExtent: WindowFrameExtent,
    val windowFrameExclusion: WindowFrameExclusion? = null,
) : Resolvable
