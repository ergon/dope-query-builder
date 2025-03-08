package ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable

private const val OVER = "OVER"

sealed interface OverClause : Resolvable

class OverClauseWindowDefinition(private val windowDefinition: WindowDefinition) : OverClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val windowDefinitionDopeQuery = windowDefinition.toDopeQuery(manager)
        return DopeQuery(
            queryString = "$OVER (${windowDefinitionDopeQuery.queryString})",
            parameters = windowDefinitionDopeQuery.parameters,
        )
    }
}

class OverClauseWindowReference(private val windowReference: String) : OverClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        return DopeQuery(
            queryString = "$OVER `$windowReference`",
        )
    }
}
