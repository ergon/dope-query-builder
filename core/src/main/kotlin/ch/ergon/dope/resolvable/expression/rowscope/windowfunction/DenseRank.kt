package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val DENSE_RANK = "DENSE_RANK"

class DenseRank : WindowFunctionExpression<NumberType> {
    constructor(
        windowOrderClause: List<OrderingTerm>,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    ) : super(
        functionName = DENSE_RANK,
        overDefinition = OverWindowDefinition(
            WindowDefinition(
                windowPartitionClause = windowPartitionClause,
                windowOrderClause = windowOrderClause,
            ),
        ),
    )

    constructor(windowReference: String) : super(
        functionName = DENSE_RANK,
        overDefinition = OverWindowReference(windowReference),
    )
}

fun denseRank(windowReference: String) = DenseRank(windowReference)

fun denseRank(
    windowOrderClause: List<OrderingTerm>,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
) = DenseRank(windowOrderClause, windowPartitionClause)
