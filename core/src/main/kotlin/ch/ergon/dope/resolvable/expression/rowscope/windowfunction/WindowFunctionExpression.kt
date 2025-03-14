package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.rowscope.RowScopeExpression
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.OverClause
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.WindowFunctionArguments
import ch.ergon.dope.validtype.ValidType

enum class FromModifier(val queryString: String) {
    FIRST("FROM FIRST"),
    LAST("FROM LAST"),
}

enum class NullsModifier(val queryString: String) {
    RESPECT("RESPECT NULLS"),
    IGNORE("IGNORE NULLS"),
}

sealed class WindowFunctionExpression<T : ValidType>(
    private val functionName: String,
    private val windowFunctionArguments: WindowFunctionArguments? = null,
    private val fromModifier: FromModifier? = null,
    private val nullsModifier: NullsModifier? = null,
    private val overClause: OverClause,
) : RowScopeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val windowFunctionArgumentsDopeQuery = windowFunctionArguments?.toDopeQuery(manager)
        val overClauseDopeQuery = overClause.toDopeQuery(manager)
        return DopeQuery(
            queryString = functionName +
                (windowFunctionArgumentsDopeQuery?.queryString?.let { " ($it)" } ?: " ()") +
                (fromModifier?.let { " ${it.queryString}" }.orEmpty()) +
                (nullsModifier?.let { " ${it.queryString}" }.orEmpty()) +
                overClauseDopeQuery.queryString.let { " $it" },
            parameters = windowFunctionArgumentsDopeQuery?.parameters?.merge(overClauseDopeQuery.parameters)
                ?: overClauseDopeQuery.parameters,
        )
    }
}
