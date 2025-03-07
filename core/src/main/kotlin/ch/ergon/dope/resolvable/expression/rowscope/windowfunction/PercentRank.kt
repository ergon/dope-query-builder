package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val PERCENT_RANK = "PERCENT_RANK"

class PercentRank : WindowFunction<NumberType> {
    constructor(
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>,
    ) : super(
        functionName = PERCENT_RANK,
        overClause = OverClauseWindowDefinition(
            WindowDefinition(
                windowPartitionClause = windowPartitionClause,
                windowOrderClause = windowOrderClause,
            ),
        ),
    )

    constructor(windowReference: String) : super(
        functionName = PERCENT_RANK,
        overClause = OverClauseWindowReference(windowReference),
    )
}

fun percentRank(windowReference: String) = PercentRank(windowReference)

fun percentRank(
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>
) = PercentRank(windowPartitionClause, windowOrderClause)
