package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val NTH_VALUE = "NTH_VALUE"

class NthValue : WindowFunction {
    constructor(
        expression: UnaliasedExpression<out ValidType>,
        offset: TypeExpression<NumberType>,
        nullsModifier: NullsModifier? = null,
        fromModifier: FromModifier? = null,
        windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ) : super(
        functionName = NTH_VALUE,
        windowFunctionArguments = WindowFunctionArguments(expression, offset),
        fromModifier = fromModifier,
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
        expression: UnaliasedExpression<out ValidType>,
        offset: TypeExpression<NumberType>,
        nullsModifier: NullsModifier? = null,
        fromModifier: FromModifier? = null,
        windowReference: String,
    ) : super(
        functionName = NTH_VALUE,
        windowFunctionArguments = WindowFunctionArguments(expression, offset),
        fromModifier = fromModifier,
        nullsModifier = nullsModifier,
        overClause = OverClauseWindowReference(windowReference),
    )
}

fun nthValue(
    expression: UnaliasedExpression<out ValidType>,
    offset: TypeExpression<NumberType>,
    nullsModifier: NullsModifier? = null,
    fromModifier: FromModifier? = null,
    windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = NthValue(
    expression,
    offset,
    nullsModifier,
    fromModifier,
    windowPartitionClause,
    windowOrderClause,
    windowFrameClause,
)

fun nthValue(
    expression: UnaliasedExpression<out ValidType>,
    offset: TypeExpression<NumberType>,
    nullsModifier: NullsModifier? = null,
    fromModifier: FromModifier? = null,
    windowReference: String,
) = NthValue(
    expression,
    offset,
    nullsModifier,
    fromModifier,
    windowReference,
)
