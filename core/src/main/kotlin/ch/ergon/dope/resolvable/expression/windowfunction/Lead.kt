package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val LEAD = "LEAD"

class Lead : WindowFunction {
    constructor(
        expression: UnaliasedExpression<out ValidType>,
        offset: TypeExpression<NumberType>? = null,
        default: UnaliasedExpression<out ValidType>? = null,
        nullsModifier: NullsModifier? = null,
        windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>,
    ) : super(
        functionName = LEAD,
        windowFunctionArguments = WindowFunctionArguments(expression, offset, default),
        fromModifier = null,
        nullsModifier = nullsModifier,
        overClause = OverClause(
            WindowDefinition(
                windowPartitionClause = windowPartitionClause,
                windowOrderClause = windowOrderClause,
                windowFrameClause = null,
            ),
        ),
    )

    constructor(
        expression: UnaliasedExpression<out ValidType>,
        offset: TypeExpression<NumberType>? = null,
        default: UnaliasedExpression<out ValidType>? = null,
        nullsModifier: NullsModifier? = null,
        windowReference: String,
    ) : super(
        functionName = LEAD,
        windowFunctionArguments = WindowFunctionArguments(expression, offset, default),
        fromModifier = null,
        nullsModifier = nullsModifier,
        overClause = OverClause(windowReference),
    )
}

fun lead(
    expression: UnaliasedExpression<out ValidType>,
    offset: TypeExpression<NumberType>? = null,
    default: UnaliasedExpression<out ValidType>? = null,
    nullsModifier: NullsModifier? = null,
    windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>,
) = Lead(expression, offset, default, nullsModifier, windowPartitionClause, windowOrderClause)

fun lead(
    expression: UnaliasedExpression<out ValidType>,
    offset: TypeExpression<NumberType>? = null,
    default: UnaliasedExpression<out ValidType>? = null,
    nullsModifier: NullsModifier? = null,
    windowReference: String,
) = Lead(expression, offset, default, nullsModifier, windowReference)
