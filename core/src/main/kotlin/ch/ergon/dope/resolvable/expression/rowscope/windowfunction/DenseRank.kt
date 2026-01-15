package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

data class DenseRank(
    val windowOrderClause: List<OrderingTerm>,
    val windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
) : WindowFunctionExpression<NumberType> {
    override val quantifier: AggregateQuantifier? = null
    override val functionArguments: List<Selectable?> = emptyList()
    override val fromModifier: FromModifier? = null
    override val nullsModifier: NullsModifier? = null
    override val overDefinition: OverDefinition = OverWindowDefinition(
        WindowDefinition(
            windowPartitionClause = windowPartitionClause,
            windowOrderClause = windowOrderClause,
        ),
    )
}

data class DenseRankWithReference(val windowReference: String) : WindowFunctionExpression<NumberType> {
    override val quantifier: AggregateQuantifier? = null
    override val functionArguments: List<Selectable?> = emptyList()
    override val fromModifier: FromModifier? = null
    override val nullsModifier: NullsModifier? = null
    override val overDefinition: OverDefinition = OverWindowReference(windowReference)
}

fun denseRank(windowReference: String) = DenseRankWithReference(windowReference)

fun denseRank(
    windowOrderClause: List<OrderingTerm>,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
) = DenseRank(windowOrderClause, windowPartitionClause)
