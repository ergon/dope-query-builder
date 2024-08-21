package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.ValidType

private const val ROW_NUMBER = "ROW_NUMBER"

class RowNumber : WindowFunction {
    constructor(
        windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
    ) : super(
        functionName = ROW_NUMBER,
        overClause = OverClauseWindowDefinition(
            WindowDefinition(
                windowPartitionClause = windowPartitionClause,
                windowOrderClause = windowOrderClause,
            ),
        ),
    )

    constructor(windowReference: String) : super(
        functionName = ROW_NUMBER,
        overClause = OverClauseWindowReference(windowReference),
    )
}

fun rowNumber(windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null, windowOrderClause: List<OrderingTerm>? = null) =
    RowNumber(windowPartitionClause, windowOrderClause)

fun rowNumber(windowReference: String) = RowNumber(windowReference)
