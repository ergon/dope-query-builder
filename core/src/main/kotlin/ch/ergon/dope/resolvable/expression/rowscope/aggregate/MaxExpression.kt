package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameClause
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

private const val MAX = "MAX"

class MaxExpression<T : ValidType> : AggregateFunctionExpression<T> {
    constructor(
        field: Field<T>,
        windowReference: String,
        quantifier: AggregateQuantifier? = null,
    ) : super(MAX, field, quantifier, OverWindowReference(windowReference))

    constructor(
        field: Field<T>,
        quantifier: AggregateQuantifier? = null,
        windowReference: TypeExpression<StringType>? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ) : super(
        MAX,
        field,
        quantifier,
        if (listOf(windowReference, windowPartitionClause, windowOrderClause, windowFrameClause).all { it == null }) {
            null
        } else {
            OverWindowDefinition(WindowDefinition(windowReference, windowPartitionClause, windowOrderClause, windowFrameClause))
        },
    )
}

fun max(
    field: Field<out ValidType>,
    windowReference: String,
    quantifier: AggregateQuantifier? = null,
) = MaxExpression(field, windowReference, quantifier)

fun max(
    field: Field<out ValidType>,
    quantifier: AggregateQuantifier? = null,
    windowReference: TypeExpression<StringType>? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = MaxExpression(field, quantifier, windowReference, windowPartitionClause, windowOrderClause, windowFrameClause)
