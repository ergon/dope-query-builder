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
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

data class NthValue<T : ValidType>(
    val expression: TypeExpression<T>,
    val offset: TypeExpression<NumberType>,
    override val nullsModifier: NullsModifier? = null,
    override val fromModifier: FromModifier? = null,
    val windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    val windowOrderClause: List<OrderingTerm>? = null,
    val windowFrameClause: WindowFrameClause? = null,
) : WindowFunctionExpression<T> {
    override val quantifier: AggregateQuantifier? = null
    override val functionArguments: List<Selectable?> = listOf(expression, offset)
    override val overDefinition: OverDefinition? = OverWindowDefinition(
        WindowDefinition(
            windowPartitionClause = windowPartitionClause,
            windowOrderClause = windowOrderClause,
            windowFrameClause = windowFrameClause,
        ),
    )
}

data class NthValueWithReference<T : ValidType>(
    val expression: TypeExpression<T>,
    val offset: TypeExpression<NumberType>,
    override val nullsModifier: NullsModifier? = null,
    override val fromModifier: FromModifier? = null,
    val windowReference: String,
) : WindowFunctionExpression<T> {
    override val quantifier: AggregateQuantifier? = null
    override val functionArguments: List<Selectable?> = listOf(expression, offset)
    override val overDefinition: OverDefinition = OverWindowReference(windowReference)
}

fun <T : ValidType> nthValue(
    expression: TypeExpression<T>,
    offset: TypeExpression<NumberType>,
    nullsModifier: NullsModifier? = null,
    fromModifier: FromModifier? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = NthValue(
    expression,
    offset,
    nullsModifier,
    fromModifier,
    windowPartitionClause,
    windowOrderClause,
    windowFrameClause,
)

fun <T : ValidType> nthValue(
    expression: TypeExpression<T>,
    offset: Number,
    nullsModifier: NullsModifier? = null,
    fromModifier: FromModifier? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = nthValue(
    expression,
    offset.toDopeType(),
    nullsModifier,
    fromModifier,
    windowPartitionClause,
    windowOrderClause,
    windowFrameClause,
)

fun <T : ValidType> nthValue(
    expression: TypeExpression<T>,
    offset: TypeExpression<NumberType>,
    nullsModifier: NullsModifier? = null,
    fromModifier: FromModifier? = null,
    windowReference: String,
) = NthValueWithReference(expression, offset, nullsModifier, fromModifier, windowReference)

fun <T : ValidType> nthValue(
    expression: TypeExpression<T>,
    offset: Number,
    nullsModifier: NullsModifier? = null,
    fromModifier: FromModifier? = null,
    windowReference: String,
) = nthValue(expression, offset.toDopeType(), nullsModifier, fromModifier, windowReference)
