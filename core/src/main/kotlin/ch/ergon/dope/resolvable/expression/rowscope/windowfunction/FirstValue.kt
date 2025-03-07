package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

private const val FIRST_VALUE = "FIRST_VALUE"

class FirstValue<T : ValidType> : WindowFunction<T> {
    constructor(
        expression: TypeExpression<T>,
        nullsModifier: NullsModifier? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
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

    constructor(
        expression: TypeExpression<T>,
        windowReference: String,
        nullsModifier: NullsModifier? = null
    ) : super(
        functionName = FIRST_VALUE,
        windowFunctionArguments = WindowFunctionArguments(expression),
        nullsModifier = nullsModifier,
        overClause = OverClauseWindowReference(windowReference),
    )
}

fun <T : ValidType> firstValue(
    expression: TypeExpression<T>,
    nullsModifier: NullsModifier? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>,
    windowFrameClause: WindowFrameClause? = null,
) = FirstValue(expression, nullsModifier, windowPartitionClause, windowOrderClause, windowFrameClause)

fun <T : ValidType> firstValue(
    expression: TypeExpression<T>,
    windowReference: String,
    nullsModifier: NullsModifier? = null,
) = FirstValue(expression, windowReference, nullsModifier)
