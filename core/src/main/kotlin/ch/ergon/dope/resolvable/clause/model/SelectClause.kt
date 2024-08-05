package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.ISelectClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.resolvable.fromable.AliasedBucket

class SelectClause(private val expression: Expression, private vararg val expressions: Expression) : ISelectClause {

    override fun toDopeQuery(): DopeQuery {
        val expressionDopeQuery = getExpressionDopeQuery(expression)
        val expressionsDopeQuery = expressions.map { getExpressionDopeQuery(it) }
        return DopeQuery(
            queryString = formatToQueryString(
                "SELECT",
                expressionDopeQuery.queryString,
                *expressionsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = expressionsDopeQuery.fold(expressionDopeQuery.parameters) { expressionParameters, field ->
                expressionParameters + field.parameters
            },
        )
    }
}

class SelectRawClause(private val expression: SingleExpression) : ISelectClause {

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
        val expressionDopeQuery = getExpressionDopeQuery(expression)
        val expressionsDopeQuery = expressions.map { getExpressionDopeQuery(it) }
        return DopeQuery(
            queryString = formatToQueryString(
                "SELECT DISTINCT",
                expressionDopeQuery.queryString,
                *expressionsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = expressionsDopeQuery.fold(expressionDopeQuery.parameters) { expressionParameters, field ->
                expressionParameters + field.parameters
            },
        )
    }
}

private fun getExpressionDopeQuery(expression: Expression) =
    if (expression is AliasedBucket) DopeQuery("`${expression.alias}`", emptyMap()) else expression.toDopeQuery()
