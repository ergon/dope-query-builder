package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.ValidType

private const val PERCENT_RANK = "PERCENT_RANK"

class PercentRank : WindowFunction {
    constructor(
        windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>,
    ) : super(
        functionName = PERCENT_RANK,
        overClause = OverClause(WindowDefinition(windowPartitionClause = windowPartitionClause, windowOrderClause = windowOrderClause)),
    )

    constructor(windowReference: String) : super(
        functionName = PERCENT_RANK,
        overClause = OverClause(windowReference),
    )
}

fun percentRank(windowReference: String) = PercentRank(windowReference)

fun percentRank(windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null, windowOrderClause: List<OrderingTerm>) =
    PercentRank(windowPartitionClause, windowOrderClause)
