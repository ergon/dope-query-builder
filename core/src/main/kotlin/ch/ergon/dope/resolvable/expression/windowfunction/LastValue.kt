package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.ValidType

private const val LAST_VALUE = "LAST_VALUE"

class LastValue : WindowFunction {
    constructor(
        expression: UnaliasedExpression<out ValidType>,
        nullsModifier: NullsModifier? = null,
        windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ) : super(
        functionName = LAST_VALUE,
        windowFunctionArguments = WindowFunctionArguments(expression),
        fromModifier = null,
        nullsModifier = nullsModifier,
        overClause = OverClause(
            WindowDefinition(
                windowPartitionClause = windowPartitionClause,
                windowOrderClause = windowOrderClause,
                windowFrameClause = windowFrameClause,
            ),
        ),
    )

    constructor(expression: UnaliasedExpression<out ValidType>, nullsModifier: NullsModifier? = null, windowReference: String) : super(
        functionName = LAST_VALUE,
        windowFunctionArguments = WindowFunctionArguments(expression),
        fromModifier = null,
        nullsModifier = nullsModifier,
        overClause = OverClause(windowReference),
    )
}

fun lastValue(
    expression: UnaliasedExpression<out ValidType>,
    nullsModifier: NullsModifier? = null,
    windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = LastValue(expression, nullsModifier, windowPartitionClause, windowOrderClause, windowFrameClause)

fun lastValue(expression: UnaliasedExpression<out ValidType>, nullsModifier: NullsModifier? = null, windowReference: String) =
    LastValue(expression, nullsModifier, windowReference)
