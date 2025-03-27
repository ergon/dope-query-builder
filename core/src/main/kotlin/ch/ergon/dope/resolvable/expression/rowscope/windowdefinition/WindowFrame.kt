package ch.ergon.dope.resolvable.expression.rowscope.windowdefinition

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
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

class WindowFrameClause(
    private val windowFrameType: WindowFrameType,
    private val windowFrameExtent: WindowFrameExtent,
    private val windowFrameExclusion: WindowFrameExclusion? = null,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val windowFrameExtentDopeQuery = windowFrameExtent.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${windowFrameType.queryString} " +
                "${windowFrameExtentDopeQuery.queryString}${
                windowFrameExclusion?.let { " ${it.queryString}" }.orEmpty()
                }",
            parameters = windowFrameExtentDopeQuery.parameters,
        )
    }
}
