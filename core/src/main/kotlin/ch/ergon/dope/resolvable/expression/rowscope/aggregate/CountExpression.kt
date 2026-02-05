package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.Asterisk
import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameClause
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class CountExpressionWithReference(
    val field: IField<out ValidType>,
    val windowReference: String,
    override val quantifier: AggregateQuantifier? = null,
) : AggregateFunctionExpression<NumberType> {
    override val selectable: Selectable = field
    override val overDefinition: OverDefinition = OverWindowReference(windowReference)
}

data class CountExpression(
    val field: IField<out ValidType>,
    override val quantifier: AggregateQuantifier? = null,
    val windowReferenceExpression: TypeExpression<StringType>? = null,
    val windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    val windowOrderClause: List<OrderingTerm>? = null,
    val windowFrameClause: WindowFrameClause? = null,
) : AggregateFunctionExpression<NumberType> {
    override val selectable: Selectable = field
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

data class CountAsteriskExpressionWithReference(
    val windowReference: String,
) : AggregateFunctionExpression<NumberType> {
    override val selectable: Selectable = Asterisk()
    override val quantifier: AggregateQuantifier? = null
    override val overDefinition: OverDefinition = OverWindowReference(windowReference)
}

data class CountAsteriskExpression(
    val windowReferenceExpression: TypeExpression<StringType>? = null,
    val windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    val windowOrderClause: List<OrderingTerm>? = null,
    val windowFrameClause: WindowFrameClause? = null,
) : AggregateFunctionExpression<NumberType> {
    override val selectable: Selectable = Asterisk()
    override val quantifier: AggregateQuantifier? = null
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

fun count(
    field: IField<out ValidType>,
    quantifier: AggregateQuantifier? = null,
    windowReferenceExpression: TypeExpression<StringType>? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = CountExpression(
    field,
    quantifier,
    windowReferenceExpression,
    windowPartitionClause,
    windowOrderClause,
    windowFrameClause,
)

fun count(
    field: IField<out ValidType>,
    windowReference: String,
    quantifier: AggregateQuantifier? = null,
) = CountExpressionWithReference(field, windowReference, quantifier)

fun countAsterisk(
    windowReference: String,
) = CountAsteriskExpressionWithReference(windowReference)

fun countAsterisk(
    windowReferenceExpression: TypeExpression<StringType>? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = CountAsteriskExpression(windowReferenceExpression, windowPartitionClause, windowOrderClause, windowFrameClause)
