package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameClause
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

private const val MIN = "MIN"

data class MinExpressionWithReference<T : ValidType>(
    val field: IField<T>,
    val windowReference: String,
    override val quantifier: AggregateQuantifier? = null,
) : AggregateFunctionExpression<T> {
    override val selectable: Selectable = field
    override val functionName: String = MIN
    override val overDefinition: OverDefinition = OverWindowReference(windowReference)
}

data class MinExpression<T : ValidType>(
    val field: IField<T>,
    override val quantifier: AggregateQuantifier? = null,
    val windowReferenceExpression: TypeExpression<StringType>? = null,
    val windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    val windowOrderClause: List<OrderingTerm>? = null,
    val windowFrameClause: WindowFrameClause? = null,
) : AggregateFunctionExpression<T> {
    override val selectable: Selectable = field
    override val functionName: String = MIN
    override val overDefinition: OverDefinition? = if (listOf(
            windowReferenceExpression,
            windowPartitionClause,
            windowOrderClause,
            windowFrameClause,
        ).all { it == null }
    ) {
        null
    } else {
        OverWindowDefinition(
            WindowDefinition(
                windowReferenceExpression,
                windowPartitionClause,
                windowOrderClause,
                windowFrameClause,
            ),
        )
    }
}

fun min(
    field: IField<out ValidType>,
    windowReference: String,
    quantifier: AggregateQuantifier? = null,
) = MinExpressionWithReference(field, windowReference, quantifier)

fun min(
    field: IField<out ValidType>,
    quantifier: AggregateQuantifier? = null,
    windowReferenceExpression: TypeExpression<StringType>? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = MinExpression(
    field,
    quantifier,
    windowReferenceExpression,
    windowPartitionClause,
    windowOrderClause,
    windowFrameClause,
)
