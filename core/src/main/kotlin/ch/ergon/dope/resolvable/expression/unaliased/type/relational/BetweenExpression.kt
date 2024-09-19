package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType

class BetweenExpression<T : ComparableType>(
    private val expression: TypeExpression<T>,
    private val start: TypeExpression<T>,
    private val end: TypeExpression<T>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = when (expression) {
            is ISelectOffsetClause<*> -> expression.asSubQuery().toDopeQuery(manager)
            else -> expression.toDopeQuery(manager)
        }
        val startDopeQuery = when (start) {
            is ISelectOffsetClause<*> -> start.asSubQuery().toDopeQuery(manager)
            else -> start.toDopeQuery(manager)
        }
        val endDopeQuery = when (end) {
            is ISelectOffsetClause<*> -> end.asSubQuery().toDopeQuery(manager)
            else -> end.toDopeQuery(manager)
        }
        return DopeQuery(
            queryString = "${expressionDopeQuery.queryString} BETWEEN ${startDopeQuery.queryString} AND ${endDopeQuery.queryString}",
            parameters = expressionDopeQuery.parameters + startDopeQuery.parameters + endDopeQuery.parameters,
        )
    }
}

fun <T : ComparableType> TypeExpression<T>.between(start: TypeExpression<T>, end: TypeExpression<T>) =
    BetweenExpression(this, start, end)

class NotBetweenExpression<T : ComparableType>(
    private val expression: TypeExpression<T>,
    private val start: TypeExpression<T>,
    private val end: TypeExpression<T>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = when (expression) {
            is ISelectOffsetClause<*> -> expression.asSubQuery().toDopeQuery(manager)
            else -> expression.toDopeQuery(manager)
        }
        val startDopeQuery = when (start) {
            is ISelectOffsetClause<*> -> start.asSubQuery().toDopeQuery(manager)
            else -> start.toDopeQuery(manager)
        }
        val endDopeQuery = when (end) {
            is ISelectOffsetClause<*> -> end.asSubQuery().toDopeQuery(manager)
            else -> end.toDopeQuery(manager)
        }
        return DopeQuery(
            queryString = "${expressionDopeQuery.queryString} NOT BETWEEN ${startDopeQuery.queryString} AND ${endDopeQuery.queryString}",
            parameters = expressionDopeQuery.parameters + startDopeQuery.parameters + endDopeQuery.parameters,
        )
    }
}

fun <T : ComparableType> TypeExpression<T>.notBetween(start: TypeExpression<T>, end: TypeExpression<T>) =
    NotBetweenExpression(this, start, end)
