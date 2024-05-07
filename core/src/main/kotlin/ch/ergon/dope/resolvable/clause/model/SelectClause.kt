package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.ISelectClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.formatToQueryString

class SelectClause(private val expression: Expression, private vararg val expressions: Expression) : ISelectClause {

    override fun toQuery(): DopeQuery {
        val expressionDopeQuery = expression.toQuery()
        val expressionsDopeQuery = expressions.map { it.toQuery() }
        return DopeQuery(
            queryString = formatToQueryString(
                "SELECT",
                expressionDopeQuery.queryString,
                *expressionsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = expressionDopeQuery.parameters + expressionsDopeQuery.fold(emptyMap()) { map, field -> map + field.parameters },
        )
    }
}

class SelectRawClause(private val expression: Expression) : ISelectClause {

    override fun toQuery(): DopeQuery {
        val expressionDopeQuery = expression.toQuery()
        return DopeQuery(
            formatToQueryString("SELECT RAW", expressionDopeQuery.queryString),
            expressionDopeQuery.parameters,
        )
    }
}

class SelectDistinctClause(private val expression: Expression, private vararg val expressions: Expression) : ISelectClause {
    override fun toQuery(): DopeQuery {
        val expressionsDopeQuery = expressions.map { it.toQuery() }
        val expressionDopeQuery = expression.toQuery()
        return DopeQuery(
            queryString = formatToQueryString(
                "SELECT DISTINCT",
                expressionDopeQuery.queryString,
                *expressionsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = expressionDopeQuery.parameters + expressionsDopeQuery.fold(emptyMap()) { map, field -> map + field.parameters },
        )
    }
}
