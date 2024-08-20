package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.ValidType

private const val CUME_DIST = "CUME_DIST"

class CumeDist : WindowFunction {
    constructor(
        windowOrderClause: List<OrderingTerm>,
        windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
    ) : super(
        functionName = CUME_DIST,
        windowFunctionArguments = null,
        fromModifier = null,
        nullsModifier = null,
        overClause = OverClause(WindowDefinition(windowPartitionClause = windowPartitionClause, windowOrderClause = windowOrderClause)),
    )

    constructor(windowReference: String) : super(
        functionName = CUME_DIST,
        windowFunctionArguments = null,
        fromModifier = null,
        nullsModifier = null,
        overClause = OverClause(windowReference),
    )
}

fun cumeDist(windowReference: String) = CumeDist(windowReference)

fun cumeDist(windowOrderClause: List<OrderingTerm>, windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null) =
    CumeDist(windowOrderClause, windowPartitionClause)
