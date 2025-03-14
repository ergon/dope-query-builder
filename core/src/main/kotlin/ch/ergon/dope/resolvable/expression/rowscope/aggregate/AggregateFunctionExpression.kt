package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.rowscope.RowScopeExpression
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.OverClause
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.OverClauseWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.OverClauseWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.WindowFrameClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.util.formatToQueryString
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

sealed class AggregateFunctionExpression<T : ValidType>(
    private val symbol: String,
    private val selectable: Selectable,
    private val quantifier: AggregateQuantifier?,
) : FunctionOperator, RowScopeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val selectableDopeQuery = selectable.toDopeQuery(manager)
        val quantifierString = quantifier?.let { "${quantifier.queryString} " }.orEmpty()
        val resolvableQuantifierString = quantifierString + selectableDopeQuery.queryString
        return DopeQuery(
            queryString = toFunctionQueryString(symbol, resolvableQuantifierString),
            parameters = selectableDopeQuery.parameters,
        )
    }

    fun withWindow(
        windowReference: TypeExpression<StringType>? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ): AggregateFunctionExpression<T> = AggregateFunctionWithWindowExpression(
        symbol,
        selectable,
        quantifier,
        OverClauseWindowDefinition(WindowDefinition(windowReference, windowPartitionClause, windowOrderClause, windowFrameClause)),
    )

    fun withWindow(
        windowReference: String,
    ): AggregateFunctionExpression<T> =
        AggregateFunctionWithWindowExpression(symbol, selectable, quantifier, OverClauseWindowReference(windowReference))
}

private class AggregateFunctionWithWindowExpression<T : ValidType>(
    symbol: String,
    selectable: Selectable,
    quantifier: AggregateQuantifier?,
    private val overClause: OverClause,
) : AggregateFunctionExpression<T>(symbol, selectable, quantifier) {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val aggregateFunctionDopeQuery = super.toDopeQuery(manager)
        val overClauseDopeQuery = overClause.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryString(aggregateFunctionDopeQuery.queryString, overClauseDopeQuery.queryString),
            parameters = aggregateFunctionDopeQuery.parameters.merge(overClauseDopeQuery.parameters),
        )
    }
}
