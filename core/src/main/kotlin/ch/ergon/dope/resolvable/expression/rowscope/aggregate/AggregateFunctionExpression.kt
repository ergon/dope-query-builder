package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.rowscope.RowScopeExpression
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.ValidType

sealed class AggregateFunctionExpression<T : ValidType>(
    private val symbol: String,
    private val field: Field<out ValidType>,
    private val quantifier: AggregateQuantifier?,
) : FunctionOperator, RowScopeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery(manager)
        return DopeQuery(
            queryString = quantifier?.let {
                "$symbol(${quantifier.queryString} ${fieldDopeQuery.queryString})"
            } ?: toFunctionQueryString(symbol, fieldDopeQuery.queryString),
            parameters = fieldDopeQuery.parameters,
        )
    }
}
