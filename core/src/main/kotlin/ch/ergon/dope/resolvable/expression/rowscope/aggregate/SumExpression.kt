package ch.ergon.dope.resolvable.expression.rowscope.aggregate

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

private const val SUM = "SUM"

class SumExpression : AggregateFunctionExpression<NumberType> {
    constructor(
        number: Field<NumberType>,
        windowReference: String,
        quantifier: AggregateQuantifier? = null,
    ) : super(SUM, number, quantifier, OverWindowReference(windowReference))

    constructor(
        number: Field<NumberType>,
        quantifier: AggregateQuantifier? = null,
        windowReferenceExpression: TypeExpression<StringType>? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ) : super(
        SUM,
        number,
        quantifier,
        if (listOf(windowReferenceExpression, windowPartitionClause, windowOrderClause, windowFrameClause).all { it == null }) {
            null
        } else {
            OverWindowDefinition(WindowDefinition(windowReferenceExpression, windowPartitionClause, windowOrderClause, windowFrameClause))
        },
    )
}

fun sum(
    number: Field<NumberType>,
    windowReference: String,
    quantifier: AggregateQuantifier? = null,
) = SumExpression(number, windowReference, quantifier)

fun sum(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier? = null,
    windowReferenceExpression: TypeExpression<StringType>? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = SumExpression(number, quantifier, windowReferenceExpression, windowPartitionClause, windowOrderClause, windowFrameClause)
