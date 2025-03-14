package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.clause.ISelectWithClause
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ValidType

const val WITH = "WITH"

class WithClause(
    private val with: With<out ValidType>,
    private vararg val withs: With<out ValidType>,
) : ISelectWithClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val withDopeQuery = with.toDefinitionDopeQuery(manager)
        val withDopeQueries = withs.map { it.toDefinitionDopeQuery(manager) }.toTypedArray()
        return DopeQuery(
            queryString = "$WITH ${listOf(withDopeQuery, *withDopeQueries).joinToString(separator = ", ") { it.queryString }}",
            parameters = withDopeQuery.parameters.merge(*withDopeQueries.map { it.parameters }.toTypedArray()),
        )
    }
}

class With<T : ValidType>(
    private val alias: String,
    private val expression: SingleExpression<T>,
) : TypeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            queryString = "`$alias`",
            parameters = expressionDopeQuery.parameters,
        )
    }

    fun toDefinitionDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            queryString = "`$alias` AS (${expressionDopeQuery.queryString})",
            parameters = expressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> String.asCTE(expression: TypeExpression<T>) = With(this, expression)

fun String.asCTE(expression: String) = asCTE(expression.toDopeType())

fun String.asCTE(expression: Number) = asCTE(expression.toDopeType())

fun String.asCTE(expression: Boolean) = asCTE(expression.toDopeType())

fun <T : ValidType> String.asCTE(subquery: ISelectOffsetClause<T>) = asCTE(subquery.asExpression())
