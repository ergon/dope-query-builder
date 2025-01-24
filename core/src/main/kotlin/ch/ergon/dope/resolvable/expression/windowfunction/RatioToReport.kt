package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.ValidType

private const val RATIO_TO_REPORT = "RATIO_TO_REPORT"

class RatioToReport : WindowFunction {
    constructor(
        expression: UnaliasedExpression<out ValidType>,
        windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ) : super(
        functionName = RATIO_TO_REPORT,
        windowFunctionArguments = WindowFunctionArguments(expression),
        overClause = OverClauseWindowDefinition(
            WindowDefinition(
                windowPartitionClause = windowPartitionClause,
                windowOrderClause = windowOrderClause,
                windowFrameClause = windowFrameClause,
            ),
        ),
    )

    constructor(expression: UnaliasedExpression<out ValidType>, nullsModifier: NullsModifier? = null, windowReference: String) : super(
        functionName = RATIO_TO_REPORT,
        windowFunctionArguments = WindowFunctionArguments(expression),
        nullsModifier = nullsModifier,
        overClause = OverClauseWindowReference(windowReference),
    )
}

fun ratioToReport(
    expression: UnaliasedExpression<out ValidType>,
    windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = RatioToReport(expression, windowPartitionClause, windowOrderClause, windowFrameClause)

fun ratioToReport(
    expression: UnaliasedExpression<out ValidType>,
    nullsModifier: NullsModifier? = null,
    windowReference: String,
) = RatioToReport(expression, nullsModifier, windowReference)
