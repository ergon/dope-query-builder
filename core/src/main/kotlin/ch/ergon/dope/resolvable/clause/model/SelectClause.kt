package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.ISelectClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.formatToQueryString

class SelectClause(private val expression: Expression, private vararg val expressions: Expression) : ISelectClause {

    override fun toDopeQuery(): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery()
        val expressionsDopeQuery = expressions.map { it.toDopeQuery() }
        return DopeQuery(
            queryString = formatToQueryString(
                "SELECT",
                expressionDopeQuery.queryString,
                *expressionsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = expressionDopeQuery.parameters + expressionsDopeQuery.fold(emptyMap()) { expressionParameters, field ->
                expressionParameters + field.parameters
            },
        )
    }
}

class SelectRawClause(private val expression: Expression) : ISelectClause {

    override fun toDopeQuery(): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery()
        return DopeQuery(
            formatToQueryString("SELECT RAW", expressionDopeQuery.queryString),
            expressionDopeQuery.parameters,
        )
    }
}

class SelectDistinctClause(private val expression: Expression, private vararg val expressions: Expression) : ISelectClause {
    override fun toDopeQuery(): DopeQuery {
        val expressionsDopeQuery = expressions.map { it.toDopeQuery() }
        val expressionDopeQuery = expression.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryString(
                "SELECT DISTINCT",
                expressionDopeQuery.queryString,
                *expressionsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = expressionDopeQuery.parameters + expressionsDopeQuery.fold(emptyMap()) { expressionParameters, field ->
                expressionParameters + field.parameters
            },
        )
    }
}
