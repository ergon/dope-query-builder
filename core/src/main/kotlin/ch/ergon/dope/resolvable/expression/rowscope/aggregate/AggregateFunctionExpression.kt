package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.rowscope.RowScopeExpression
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.OverClause
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.OverClauseWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.OverClauseWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.WindowFrameClause
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import kotlin.reflect.full.primaryConstructor

sealed class AggregateFunctionExpression<T : ValidType>(
    val symbol: String,
    val field: Field<out ValidType>,
    val quantifier: AggregateQuantifier?,
    val overClause: OverClause? = null,
) : FunctionOperator, RowScopeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery(manager)
        val overClauseDopeQuery = overClause?.toDopeQuery(manager)
        val quantifierString = quantifier?.let { "${quantifier.queryString} " }.orEmpty()
        val fieldQuantifierString = quantifierString + fieldDopeQuery.queryString
        return DopeQuery(
            queryString = toFunctionQueryString(symbol, fieldQuantifierString) + overClauseDopeQuery?.let { " ${it.queryString}" }.orEmpty(),
            parameters = fieldDopeQuery.parameters,
        )
    }

    fun withWindow(
        windowReference: TypeExpression<StringType>? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ): AggregateFunctionExpression<T> {
        val newOverClause = OverClauseWindowDefinition(
            WindowDefinition(windowReference, windowPartitionClause, windowOrderClause, windowFrameClause),
        )

        return this::class.primaryConstructor?.call(field, quantifier, newOverClause)
            ?: throw IllegalStateException("No valid constructor found for ${this::class.simpleName}")
    }

    fun withWindow(
        windowReference: String,
    ): AggregateFunctionExpression<T> {
        val newOverClause = OverClauseWindowReference(windowReference)

        return this::class.primaryConstructor?.call(symbol, field, quantifier, newOverClause)
            ?: throw IllegalStateException("No valid constructor found for ${this::class.simpleName}")
    }
}
