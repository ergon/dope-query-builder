package ch.ergon.dope.resolvable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.BooleanType

class WhenThenCondition(private val condition: TypeExpression<BooleanType>, private val expression: Expression) : Resolvable {
    override fun toDopeQuery(): DopeQuery {
        val parentDopeQuery = condition.toDopeQuery()
        val expressionDopeQuery = expression.toDopeQuery()
        return DopeQuery(
            queryString = "WHEN ${parentDopeQuery.queryString} THEN ${expressionDopeQuery.queryString}",
            parameters = parentDopeQuery.parameters + expressionDopeQuery.parameters,
        )
    }
}

fun whenThen(condition: TypeExpression<BooleanType>, expression: Expression) = WhenThenCondition(condition, expression)
