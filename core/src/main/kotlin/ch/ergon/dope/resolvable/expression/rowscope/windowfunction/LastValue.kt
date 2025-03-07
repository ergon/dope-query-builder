package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

private const val LAST_VALUE = "LAST_VALUE"

class LastValue<T : ValidType> : WindowFunction<T> {
    constructor(
        expression: TypeExpression<T>,
        nullsModifier: NullsModifier? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ) : super(
        functionName = LAST_VALUE,
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

    constructor(
        expression: TypeExpression<T>,
        nullsModifier: NullsModifier? = null,
        windowReference: String
    ) : super(
        functionName = LAST_VALUE,
        windowFunctionArguments = WindowFunctionArguments(expression),
        nullsModifier = nullsModifier,
        overClause = OverClauseWindowReference(windowReference),
    )
}

fun <T : ValidType> lastValue(
    expression: TypeExpression<T>,
    nullsModifier: NullsModifier? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = LastValue(expression, nullsModifier, windowPartitionClause, windowOrderClause, windowFrameClause)

fun <T : ValidType> lastValue(
    expression: TypeExpression<T>,
    nullsModifier: NullsModifier? = null,
    windowReference: String
) = LastValue(expression, nullsModifier, windowReference)
