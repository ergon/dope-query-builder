package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val DENSE_RANK = "DENSE_RANK"

class DenseRank : WindowFunction<NumberType> {
    constructor(
        windowOrderClause: List<OrderingTerm>,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    ) : super(
        functionName = DENSE_RANK,
        overClause = OverClauseWindowDefinition(
            WindowDefinition(
                windowPartitionClause = windowPartitionClause,
                windowOrderClause = windowOrderClause,
            ),
        ),
    )

    constructor(windowReference: String) : super(
        functionName = DENSE_RANK,
        overClause = OverClauseWindowReference(windowReference),
    )
}

fun denseRank(windowReference: String) = DenseRank(windowReference)

fun denseRank(
    windowOrderClause: List<OrderingTerm>,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
) = DenseRank(windowOrderClause, windowPartitionClause)
