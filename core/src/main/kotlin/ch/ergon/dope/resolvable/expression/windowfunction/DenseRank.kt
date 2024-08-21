package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.ValidType

private const val DENSE_RANK = "DENSE_RANK"

class DenseRank : WindowFunction {
    constructor(
        windowOrderClause: List<OrderingTerm>,
        windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
    ) : super(
        functionName = DENSE_RANK,
        overClause = OverClause(WindowDefinition(windowPartitionClause = windowPartitionClause, windowOrderClause = windowOrderClause)),
    )

    constructor(windowReference: String) : super(
        functionName = DENSE_RANK,
        overClause = OverClause(windowReference),
    )
}

fun denseRank(windowReference: String) = DenseRank(windowReference)

fun denseRank(windowOrderClause: List<OrderingTerm>, windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null) =
    DenseRank(windowOrderClause, windowPartitionClause)
