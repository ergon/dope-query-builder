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
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

data class RatioToReport(
    val expression: TypeExpression<out ValidType>,
    val windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    val windowOrderClause: List<OrderingTerm>? = null,
    val windowFrameClause: WindowFrameClause? = null,
) : WindowFunctionExpression<NumberType> {
    override val quantifier: AggregateQuantifier? = null
    override val functionArguments: List<Selectable?> = listOf(expression)
    override val fromModifier: FromModifier? = null
    override val nullsModifier: NullsModifier? = null
    override val overDefinition: OverDefinition = OverWindowDefinition(
        WindowDefinition(
            windowPartitionClause = windowPartitionClause,
            windowOrderClause = windowOrderClause,
            windowFrameClause = windowFrameClause,
        ),
    )
}

data class RatioToReportWithReference(
    val expression: TypeExpression<out ValidType>,
    override val nullsModifier: NullsModifier? = null,
    val windowReference: String,
) : WindowFunctionExpression<NumberType> {
    override val quantifier: AggregateQuantifier? = null
    override val functionArguments: List<Selectable?> = listOf(expression)
    override val fromModifier: FromModifier? = null
    override val overDefinition: OverDefinition = OverWindowReference(windowReference)
}

fun ratioToReport(
    expression: TypeExpression<out ValidType>,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = RatioToReport(expression, windowPartitionClause, windowOrderClause, windowFrameClause)

fun ratioToReport(
    expression: TypeExpression<out ValidType>,
    nullsModifier: NullsModifier? = null,
    windowReference: String,
) = RatioToReportWithReference(expression, nullsModifier, windowReference)
