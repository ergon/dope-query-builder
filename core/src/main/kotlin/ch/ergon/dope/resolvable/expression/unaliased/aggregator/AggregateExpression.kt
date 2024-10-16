package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ValidType

sealed class AggregateExpression<T : ValidType>(
    private val symbol: String,
    private val field: Field<T>,
    private val quantifier: AggregateQuantifier?,
) : FunctionOperator, UnaliasedExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(symbol, quantifier, fieldDopeQuery.queryString),
            parameters = fieldDopeQuery.parameters,
        )
    }
}

enum class AggregateQuantifier {
    ALL,
    DISTINCT,
}
