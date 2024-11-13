package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectLetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ValidType

class LetClause<T : ValidType>(
    private val letExpression: LetExpression<out ValidType>,
    private vararg val letExpressions: LetExpression<out ValidType>,
    private val parentClause: ISelectLetClause<T>,
) : ISelectLetClause<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val letExpressionDopeQuery = letExpression.toDefinitionDopeQuery(manager)
        val letExpressionDopeQueries = letExpressions.map { it.toDefinitionDopeQuery(manager) }

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

class LetExpression<T : ValidType>(private val alias: String, private val expression: TypeExpression<T>) : TypeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        return DopeQuery(
            queryString = "`$alias`",
        )
    }

    fun toDefinitionDopeQuery(manager: DopeQueryManager): DopeQuery {
        val typeExpressionDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            queryString = "`$alias` = ${typeExpressionDopeQuery.queryString}",
            parameters = typeExpressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> String.assignTo(expression: TypeExpression<T>): LetExpression<T> = LetExpression(this, expression)
