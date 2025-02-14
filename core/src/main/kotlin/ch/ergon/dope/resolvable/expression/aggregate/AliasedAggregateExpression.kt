package ch.ergon.dope.resolvable.expression.aggregate

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.util.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ValidType

class AliasedAggregateExpression<T : ValidType>(
    private val aggregateExpression: AggregateExpression<T>,
    private val alias: String,
) : Expression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val aggregateExpressionDopeQuery = aggregateExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                aggregateExpressionDopeQuery.queryString,
                symbol = "AS",
                "`$alias`",
            ),
            parameters = aggregateExpressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> AggregateExpression<T>.alias(alias: String) = AliasedAggregateExpression(
    this,
    alias,
)
