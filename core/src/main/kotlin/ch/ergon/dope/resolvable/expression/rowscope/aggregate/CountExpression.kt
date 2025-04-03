package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.Asterisk
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameClause
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

private const val COUNT = "COUNT"

class CountExpression : AggregateFunctionExpression<NumberType> {
    constructor(
        field: Field<out ValidType>,
        windowReference: String,
        quantifier: AggregateQuantifier? = null,
    ) : super(COUNT, field, quantifier, OverWindowReference(windowReference))

    constructor(
        field: Field<out ValidType>,
        quantifier: AggregateQuantifier? = null,
        windowReferenceExpression: TypeExpression<StringType>? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ) : super(
        COUNT,
        field,
        quantifier,
        if (listOf(windowReferenceExpression, windowPartitionClause, windowOrderClause, windowFrameClause).all { it == null }) {
            null
        } else {
            OverWindowDefinition(WindowDefinition(windowReferenceExpression, windowPartitionClause, windowOrderClause, windowFrameClause))
        },
    )
}

class CountAsteriskExpression : AggregateFunctionExpression<NumberType> {
    constructor(
        windowReference: String,
    ) : super(COUNT, Asterisk(), quantifier = null, OverWindowReference(windowReference))

    constructor(
        windowReferenceExpression: TypeExpression<StringType>? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ) : super(
        COUNT,
        Asterisk(),
        null,
        if (listOf(windowReferenceExpression, windowPartitionClause, windowOrderClause, windowFrameClause).all { it == null }) {
            null
        } else {
            OverWindowDefinition(WindowDefinition(windowReferenceExpression, windowPartitionClause, windowOrderClause, windowFrameClause))
        },
    )
}

fun count(
    field: Field<out ValidType>,
    quantifier: AggregateQuantifier? = null,
    windowReferenceExpression: TypeExpression<StringType>? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = CountExpression(field, quantifier, windowReferenceExpression, windowPartitionClause, windowOrderClause, windowFrameClause)

fun count(
    field: Field<out ValidType>,
    windowReference: String,
    quantifier: AggregateQuantifier? = null,
) = CountExpression(field, windowReference, quantifier)

fun countAsterisk(
    windowReference: String,
) = CountAsteriskExpression(windowReference)

fun countAsterisk(
    windowReferenceExpression: TypeExpression<StringType>? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = CountAsteriskExpression(windowReferenceExpression, windowPartitionClause, windowOrderClause, windowFrameClause)
