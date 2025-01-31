package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.RawSelectable
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ValidType

interface AggregateExpression<T : ValidType> : RawSelectable<T>

sealed class AggregateFunctionExpression<T : ValidType>(
    private val symbol: String,
    private val field: Field<out ValidType>,
    private val quantifier: AggregateQuantifier?,
) : FunctionOperator, AggregateExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery(manager)
        return DopeQuery(
            queryString = quantifier?.let {
                "$symbol($quantifier ${fieldDopeQuery.queryString})"
            } ?: toFunctionQueryString(symbol, fieldDopeQuery.queryString),
            parameters = fieldDopeQuery.parameters,
        )
    }
}

enum class AggregateQuantifier {
    ALL,
    DISTINCT,
}
