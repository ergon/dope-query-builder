package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectClause
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.ValidType

class SelectClause(
    private val expression: Expression,
    private vararg val expressions: Expression,
) : ISelectClause<ValidType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = when (expression) {
            is ISelectOffsetClause<*> -> expression.asSelectWithParentheses().toDopeQuery(manager)
            else -> expression.toDopeQuery(manager)
        }
        val expressionsDopeQuery = expressions.map {
            when (it) {
                is ISelectOffsetClause<*> -> it.asSelectWithParentheses().toDopeQuery(manager)
                else -> it.toDopeQuery(manager)
            }
        }
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

class SelectRawClause<T : ValidType>(private val expression: SingleExpression<T>) : ISelectClause<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            formatToQueryString("SELECT RAW", expressionDopeQuery.queryString),
            expressionDopeQuery.parameters,
        )
    }
}

class SelectDistinctClause(private val expression: Expression, private vararg val expressions: Expression) : ISelectClause<ValidType> {
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
        )
    }
}
