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

data class FirstValue<T : ValidType>(
    val expression: TypeExpression<T>,
    override val nullsModifier: NullsModifier? = null,
    val windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    val windowOrderClause: List<OrderingTerm>,
    val windowFrameClause: WindowFrameClause? = null,
) : WindowFunctionExpression<T> {
    override val quantifier: AggregateQuantifier? = null
    override val functionArguments: List<Selectable?> = listOf(expression)
    override val fromModifier: FromModifier? = null
    override val overDefinition: OverDefinition = OverWindowDefinition(
        WindowDefinition(
            windowPartitionClause = windowPartitionClause,
            windowOrderClause = windowOrderClause,
            windowFrameClause = windowFrameClause,
        ),
    )
}

data class FirstValueWithReference<T : ValidType>(
    val expression: TypeExpression<T>,
    val windowReference: String,
    override val nullsModifier: NullsModifier? = null,
) : WindowFunctionExpression<T> {
    override val quantifier: AggregateQuantifier? = null
    override val functionArguments: List<Selectable?> = listOf(expression)
    override val fromModifier: FromModifier? = null
    override val overDefinition: OverDefinition = OverWindowReference(windowReference)
}

fun <T : ValidType> firstValue(
    expression: TypeExpression<T>,
    nullsModifier: NullsModifier? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>,
    windowFrameClause: WindowFrameClause? = null,
) = FirstValue(expression, nullsModifier, windowPartitionClause, windowOrderClause, windowFrameClause)

fun <T : ValidType> firstValue(
    expression: TypeExpression<T>,
    windowReference: String,
    nullsModifier: NullsModifier? = null,
) = FirstValueWithReference(expression, windowReference, nullsModifier)
