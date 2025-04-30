package ch.ergon.dope.resolvable.expression.rowscope.windowdefinition

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable

private const val OVER = "OVER"

sealed interface OverDefinition : Resolvable

class OverWindowDefinition(private val windowDefinition: WindowDefinition) : OverDefinition {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val windowDefinitionDopeQuery = windowDefinition.toDopeQuery(manager)
        return DopeQuery(
            queryString = "$OVER (${windowDefinitionDopeQuery.queryString})",
            parameters = windowDefinitionDopeQuery.parameters,
        )
    }
}

class OverWindowReference(private val windowReference: String) : OverDefinition {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        return DopeQuery(
            queryString = "$OVER `$windowReference`",
        )
    }
}
