package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectLetClause
import ch.ergon.dope.resolvable.expression.type.DopeVariable
import ch.ergon.dope.util.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ValidType

class LetClause<T : ValidType>(
    private val dopeVariable: DopeVariable<out ValidType>,
    private vararg val dopeVariables: DopeVariable<out ValidType>,
    private val parentClause: ISelectFromClause<T>,
) : ISelectLetClause<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val dopeVariableQuery = dopeVariable.toLetDefinitionDopeQuery(manager)
        val dopeVariablesQueries = dopeVariables.map { it.toLetDefinitionDopeQuery(manager) }

        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                parentDopeQuery.queryString,
                "LET",
                dopeVariableQuery.queryString,
                *dopeVariablesQueries.map { it.queryString }.toTypedArray(),
            ),
            parameters = parentDopeQuery.parameters.merge(
                dopeVariableQuery.parameters,
                *dopeVariablesQueries.map { it.parameters }.toTypedArray(),
            ),
        )
    }
}
