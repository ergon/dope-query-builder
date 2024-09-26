package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.resolvable.formatToQueryString

class SelectClause(private val expression: Expression, private vararg val expressions: Expression) : ISelectClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery(manager)
        val expressionsDopeQuery = expressions.map { it.toDopeQuery(manager) }
        return DopeQuery(
            queryString = formatToQueryString(
                "SELECT",
                expressionDopeQuery.queryString,
                *expressionsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = expressionsDopeQuery.fold(expressionDopeQuery.parameters) { expressionParameters, field ->
                expressionParameters + field.parameters
            },
            positionalParameters = expressionsDopeQuery.fold(expressionDopeQuery.positionalParameters) { expressionParameters, field ->
                expressionParameters + field.positionalParameters
            },
        )
    }
}

class SelectRawClause(private val expression: SingleExpression) : ISelectClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryString("SELECT RAW", expressionDopeQuery.queryString),
            parameters = expressionDopeQuery.parameters,
            positionalParameters = expressionDopeQuery.positionalParameters,
        )
    }
}

class SelectDistinctClause(private val expression: Expression, private vararg val expressions: Expression) : ISelectClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionsDopeQuery = expressions.map { it.toDopeQuery(manager) }
        val expressionDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryString(
                "SELECT DISTINCT",
                expressionDopeQuery.queryString,
                *expressionsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = expressionsDopeQuery.fold(expressionDopeQuery.parameters) { expressionParameters, field ->
                expressionParameters + field.parameters
            },
            positionalParameters = expressionsDopeQuery.fold(expressionDopeQuery.positionalParameters) { expressionParameters, field ->
                expressionParameters + field.positionalParameters
            },
        )
    }
}
