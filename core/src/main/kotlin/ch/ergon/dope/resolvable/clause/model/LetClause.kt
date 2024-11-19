package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectLetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ValidType

class LetClause<T : ValidType>(
    private val dopeVariable: DopeVariable<out ValidType>,
    private vararg val dopeVariables: DopeVariable<out ValidType>,
    private val parentClause: ISelectLetClause<T>,
) : ISelectLetClause<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val letExpressionDopeQuery = dopeVariable.toDefinitionDopeQuery(manager)
        val letExpressionDopeQueries = dopeVariables.map { it.toDefinitionDopeQuery(manager) }

        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                parentDopeQuery.queryString,
                "LET",
                letExpressionDopeQuery.queryString,
                *letExpressionDopeQueries.map { it.queryString }.toTypedArray(),
            ),
            parameters = parentDopeQuery.parameters.merge(
                letExpressionDopeQuery.parameters,
                *letExpressionDopeQueries.map { it.parameters }.toTypedArray(),
            ),
        )
    }
}

class DopeVariable<T : ValidType>(private val name: String, private val value: TypeExpression<T>) : TypeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        return DopeQuery(
            queryString = "`$name`",
        )
    }

    fun toDefinitionDopeQuery(manager: DopeQueryManager): DopeQuery {
        val valueDopeQuery = value.toDopeQuery(manager)
        return DopeQuery(
            queryString = "`$name` = ${valueDopeQuery.queryString}",
            parameters = valueDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> String.assignTo(expression: TypeExpression<T>): DopeVariable<T> = DopeVariable(this, expression)
