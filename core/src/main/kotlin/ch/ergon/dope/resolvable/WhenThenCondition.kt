package ch.ergon.dope.resolvable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class WhenThenCondition(
    private val condition: TypeExpression<BooleanType>,
    private val unaliasedExpression: UnaliasedExpression<out ValidType>,
) : Resolvable {
    override fun toDopeQuery(): DopeQuery {
        val conditionDopQuery = condition.toDopeQuery()
        val unaliasedExpressionDopeQuery = unaliasedExpression.toDopeQuery()
        return DopeQuery(
            queryString = "WHEN ${conditionDopQuery.queryString} THEN ${unaliasedExpressionDopeQuery.queryString}",
            parameters = conditionDopQuery.parameters + unaliasedExpressionDopeQuery.parameters,
        )
    }
}

fun whenThen(condition: TypeExpression<BooleanType>, expression: UnaliasedExpression<out ValidType>) = WhenThenCondition(condition, expression)
