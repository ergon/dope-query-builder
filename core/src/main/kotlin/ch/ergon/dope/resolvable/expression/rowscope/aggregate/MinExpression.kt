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

private const val MIN = "MIN"

class MinExpression<T : ValidType> : AggregateFunctionExpression<T> {
    constructor(
        field: Field<T>,
        windowReference: String,
        quantifier: AggregateQuantifier? = null,
    ) : super(MIN, field, quantifier, OverWindowReference(windowReference))

    constructor(
        field: Field<T>,
        quantifier: AggregateQuantifier? = null,
        windowReferenceExpression: TypeExpression<StringType>? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ) : super(
        MIN,
        field,
        quantifier,
        if (listOf(windowReferenceExpression, windowPartitionClause, windowOrderClause, windowFrameClause).all { it == null }) {
            null
        } else {
            OverWindowDefinition(WindowDefinition(windowReferenceExpression, windowPartitionClause, windowOrderClause, windowFrameClause))
        },
    )
}

fun min(
    field: Field<out ValidType>,
    windowReference: String,
    quantifier: AggregateQuantifier? = null,
) = MinExpression(field, windowReference, quantifier)

fun min(
    field: Field<out ValidType>,
    quantifier: AggregateQuantifier? = null,
    windowReferenceExpression: TypeExpression<StringType>? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = MinExpression(field, quantifier, windowReferenceExpression, windowPartitionClause, windowOrderClause, windowFrameClause)
