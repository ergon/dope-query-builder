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

private const val AVG = "AVG"

class AverageExpression : AggregateFunctionExpression<NumberType> {
    constructor(
        number: Field<NumberType>,
        windowReference: String,
        quantifier: AggregateQuantifier? = null,
    ) : super(AVG, number, quantifier, OverWindowReference(windowReference))

    constructor(
        number: Field<NumberType>,
        quantifier: AggregateQuantifier? = null,
        windowReference: TypeExpression<StringType>? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ) : super(
        AVG,
        number,
        quantifier,
        if (listOf(windowReference, windowPartitionClause, windowOrderClause, windowFrameClause).all { it == null }) {
            null
        } else {
            OverWindowDefinition(WindowDefinition(windowReference, windowPartitionClause, windowOrderClause, windowFrameClause))
        },
    )
}

fun avg(
    number: Field<NumberType>,
    windowReference: String,
    quantifier: AggregateQuantifier? = null,
) = AverageExpression(number, windowReference, quantifier)

fun avg(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier? = null,
    windowReference: TypeExpression<StringType>? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = AverageExpression(number, quantifier, windowReference, windowPartitionClause, windowOrderClause, windowFrameClause)
