package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.ValidType

private const val RANK = "RANK"

class Rank : WindowFunction {
    constructor(
        windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>,
    ) : super(
        functionName = RANK,
        overClause = OverClause(WindowDefinition(windowPartitionClause = windowPartitionClause, windowOrderClause = windowOrderClause)),
    )

    constructor(windowReference: String) : super(
        functionName = RANK,
        overClause = OverClause(windowReference),
    )
}

fun rank(windowReference: String) = Rank(windowReference)

fun rank(windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null, windowOrderClause: List<OrderingTerm>) =
    Rank(windowPartitionClause, windowOrderClause)
