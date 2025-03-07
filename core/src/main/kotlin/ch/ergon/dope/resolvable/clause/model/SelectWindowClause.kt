package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectWindowClause
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.WindowDefinition
import ch.ergon.dope.util.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ValidType

class SelectWindowClause<T : ValidType>(
    private val windowDeclaration: WindowDeclaration,
    private vararg val windowDeclarations: WindowDeclaration,
    private val parentClause: ISelectGroupByClause<T>,
) : ISelectWindowClause<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val windowDeclarationDopeQuery = windowDeclaration.toDopeQuery(manager)
        val windowDeclarationsDopeQueries = windowDeclarations.map { it.toDopeQuery(manager) }
        val parentDopeQuery = parentClause.toDopeQuery(manager)

        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                parentDopeQuery.queryString,
                "WINDOW",
                windowDeclarationDopeQuery.queryString,
                *windowDeclarationsDopeQueries.map { it.queryString }.toTypedArray(),
            ),
            parameters = parentDopeQuery.parameters.merge(
                windowDeclarationDopeQuery.parameters,
                *windowDeclarationsDopeQueries.map { it.parameters }.toTypedArray(),
            ),
        )
    }
}

class WindowDeclaration(val reference: String, private val windowDefinition: WindowDefinition? = null) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val windowDefinitionDopeQuery = windowDefinition?.toDopeQuery(manager)

        return DopeQuery(
            queryString = "$reference AS ${windowDefinitionDopeQuery?.let { "(${it.queryString})" } ?: "()"}",
            parameters = windowDefinitionDopeQuery?.parameters ?: DopeParameters(),
        )
    }
}

fun String.asWindowDeclaration(windowDefinition: WindowDefinition? = null) = WindowDeclaration(this, windowDefinition)
