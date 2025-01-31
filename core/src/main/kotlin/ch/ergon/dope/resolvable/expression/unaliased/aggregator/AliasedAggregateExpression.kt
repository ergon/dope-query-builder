package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.resolvable.fromable.RawSelectable
import ch.ergon.dope.validtype.ValidType

class AliasedAggregateExpression<T : ValidType>(
    private val aggregateExpression: AggregateExpression<T>,
    private val alias: String,
) : RawSelectable<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val aggregateExpressionDopeQuery = aggregateExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                aggregateExpressionDopeQuery.queryString,
                "AS",
                "`$alias`",
            ),
            parameters = aggregateExpressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> AggregateExpression<T>.alias(alias: String) = AliasedAggregateExpression(this, alias)
