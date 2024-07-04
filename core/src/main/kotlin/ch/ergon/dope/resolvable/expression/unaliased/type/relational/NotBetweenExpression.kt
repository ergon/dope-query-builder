package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ComparableType

class NotBetweenExpression<T : ComparableType>(
    private val expression: TypeExpression<T>,
    private val start: TypeExpression<T>,
    private val end: TypeExpression<T>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery()
        val startDopeQuery = start.toDopeQuery()
        val endDopeQuery = end.toDopeQuery()
        return DopeQuery(
            queryString = "${expressionDopeQuery.queryString} NOT BETWEEN ${startDopeQuery.queryString} AND ${endDopeQuery.queryString}",
            parameters = expressionDopeQuery.parameters + startDopeQuery.parameters + endDopeQuery.parameters,
        )
    }
}

fun <T : ComparableType> TypeExpression<T>.notBetween(start: TypeExpression<T>, end: TypeExpression<T>) =
    NotBetweenExpression(this, start, end)
