package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

private const val LAST_VALUE = "LAST_VALUE"

class LastValue<T : ValidType>(
    val expression: TypeExpression<T>,
    override val nullsModifier: NullsModifier? = null,
    val windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    val windowOrderClause: List<OrderingTerm>? = null,
    val windowFrameClause: WindowFrameClause? = null,
) : WindowFunctionExpression<T> {
    override val functionName: String = LAST_VALUE
    override val quantifier: AggregateQuantifier? = null
    override val functionArguments: List<Selectable?> = listOf(expression)
    override val fromModifier: FromModifier? = null
    override val overDefinition: OverDefinition? = OverWindowDefinition(
        WindowDefinition(
            windowPartitionClause = windowPartitionClause,
            windowOrderClause = windowOrderClause,
            windowFrameClause = windowFrameClause,
        ),
    )
}

class LastValueWithReference<T : ValidType>(
    val expression: TypeExpression<T>,
    override val nullsModifier: NullsModifier? = null,
    val windowReference: String,
) : WindowFunctionExpression<T> {
    override val functionName: String = LAST_VALUE
    override val quantifier: AggregateQuantifier? = null
    override val functionArguments: List<Selectable?> = listOf(expression)
    override val fromModifier: FromModifier? = null
    override val overDefinition: OverDefinition = OverWindowReference(windowReference)
}

fun <T : ValidType> lastValue(
    expression: TypeExpression<T>,
    nullsModifier: NullsModifier? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = LastValue(expression, nullsModifier, windowPartitionClause, windowOrderClause, windowFrameClause)

fun <T : ValidType> lastValue(
    expression: TypeExpression<T>,
    nullsModifier: NullsModifier? = null,
    windowReference: String,
) = LastValueWithReference(expression, nullsModifier, windowReference)
