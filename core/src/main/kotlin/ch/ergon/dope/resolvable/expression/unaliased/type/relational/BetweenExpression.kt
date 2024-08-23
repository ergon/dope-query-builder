package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType

class BetweenExpression<T : ComparableType>(
    private val expression: TypeExpression<T>,
    private val start: TypeExpression<T>,
    private val end: TypeExpression<T>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery(manager)
        val startDopeQuery = start.toDopeQuery(manager)
        val endDopeQuery = end.toDopeQuery(manager)
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
        val expressionDopeQuery = expression.toDopeQuery(manager)
        val startDopeQuery = start.toDopeQuery(manager)
        val endDopeQuery = end.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${expressionDopeQuery.queryString} NOT BETWEEN ${startDopeQuery.queryString} AND ${endDopeQuery.queryString}",
            parameters = expressionDopeQuery.parameters + startDopeQuery.parameters + endDopeQuery.parameters,
        )
    }
}

fun <T : ComparableType> TypeExpression<T>.notBetween(start: TypeExpression<T>, end: TypeExpression<T>) =
    NotBetweenExpression(this, start, end)
