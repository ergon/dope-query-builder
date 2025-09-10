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
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

private const val MEDIAN = "MEDIAN"

data class MedianExpressionWithReference(
    val number: IField<NumberType>,
    val windowReference: String,
    override val quantifier: AggregateQuantifier? = null,
) : AggregateFunctionExpression<NumberType> {
    override val selectable: Selectable = number
    override val functionName: String = MEDIAN
    override val overDefinition: OverDefinition = OverWindowReference(windowReference)
}

data class MedianExpression(
    val number: IField<NumberType>,
    override val quantifier: AggregateQuantifier? = null,
    val windowReferenceExpression: TypeExpression<StringType>? = null,
    val windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    val windowOrderClause: List<OrderingTerm>? = null,
    val windowFrameClause: WindowFrameClause? = null,
) : AggregateFunctionExpression<NumberType> {
    override val selectable: Selectable = number
    override val functionName: String = MEDIAN
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

fun median(
    number: IField<NumberType>,
    windowReference: String,
    quantifier: AggregateQuantifier? = null,
) = MedianExpressionWithReference(number, windowReference, quantifier)

fun median(
    number: IField<NumberType>,
    quantifier: AggregateQuantifier? = null,
    windowReferenceExpression: TypeExpression<StringType>? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = MedianExpression(
    number,
    quantifier,
    windowReferenceExpression,
    windowPartitionClause,
    windowOrderClause,
    windowFrameClause,
)
