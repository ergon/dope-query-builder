package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.ValidType

private const val FIRST_VALUE = "FIRST_VALUE"

class FirstValue : WindowFunction {
    constructor(
        expression: UnaliasedExpression<out ValidType>,
        nullsModifier: NullsModifier? = null,
        windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>,
        windowFrameClause: WindowFrameClause? = null,
    ) : super(
        functionName = FIRST_VALUE,
        windowFunctionArguments = WindowFunctionArguments(expression),
        nullsModifier = nullsModifier,
        overClause = OverClauseWindowDefinition(
            WindowDefinition(
                windowPartitionClause = windowPartitionClause,
                windowOrderClause = windowOrderClause,
                windowFrameClause = windowFrameClause,
            ),
        ),
    )

    constructor(expression: UnaliasedExpression<out ValidType>, windowReference: String, nullsModifier: NullsModifier? = null) : super(
        functionName = FIRST_VALUE,
        windowFunctionArguments = WindowFunctionArguments(expression),
        nullsModifier = nullsModifier,
        overClause = OverClauseWindowReference(windowReference),
    )
}

fun firstValue(
    expression: UnaliasedExpression<out ValidType>,
    nullsModifier: NullsModifier? = null,
    windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>,
    windowFrameClause: WindowFrameClause? = null,
) = FirstValue(expression, nullsModifier, windowPartitionClause, windowOrderClause, windowFrameClause)

fun firstValue(
    expression: UnaliasedExpression<out ValidType>,
    windowReference: String,
    nullsModifier: NullsModifier? = null,
) = FirstValue(expression, windowReference, nullsModifier)
