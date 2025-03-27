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

private const val STDDEV = "STDDEV"

class StandardDeviationExpression : AggregateFunctionExpression<NumberType> {
    constructor(
        number: Field<NumberType>,
        windowReference: String,
        quantifier: AggregateQuantifier? = null,
    ) : super(STDDEV, number, quantifier, OverWindowReference(windowReference))

    constructor(
        number: Field<NumberType>,
        quantifier: AggregateQuantifier? = null,
        windowReference: TypeExpression<StringType>? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ) : super(
        STDDEV,
        number,
        quantifier,
        if (listOf(windowReference, windowPartitionClause, windowOrderClause, windowFrameClause).all { it == null }) {
            null
        } else {
            OverWindowDefinition(WindowDefinition(windowReference, windowPartitionClause, windowOrderClause, windowFrameClause))
        },
    )
}

fun stdDev(
    number: Field<NumberType>,
    windowReference: String,
    quantifier: AggregateQuantifier? = null,
) = StandardDeviationExpression(number, windowReference, quantifier)

fun stdDev(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier? = null,
    windowReference: TypeExpression<StringType>? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = StandardDeviationExpression(number, quantifier, windowReference, windowPartitionClause, windowOrderClause, windowFrameClause)
