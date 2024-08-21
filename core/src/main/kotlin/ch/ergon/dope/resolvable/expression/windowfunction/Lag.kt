package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val LAG = "LAG"

class Lag : WindowFunction {
    constructor(
        expression: UnaliasedExpression<out ValidType>,
        offset: TypeExpression<NumberType>? = null,
        default: UnaliasedExpression<out ValidType>? = null,
        nullsModifier: NullsModifier? = null,
        windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>,
    ) : super(
        functionName = LAG,
        windowFunctionArguments = WindowFunctionArguments(expression, offset, default),
        nullsModifier = nullsModifier,
        overClause = OverClause(
            WindowDefinition(
                windowPartitionClause = windowPartitionClause,
                windowOrderClause = windowOrderClause,
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
        functionName = LAG,
        windowFunctionArguments = WindowFunctionArguments(expression, offset, default),
        nullsModifier = nullsModifier,
        overClause = OverClause(windowReference),
    )
}

fun lag(
    expression: UnaliasedExpression<out ValidType>,
    offset: TypeExpression<NumberType>? = null,
    default: UnaliasedExpression<out ValidType>? = null,
    nullsModifier: NullsModifier? = null,
    windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>,
) = Lag(expression, offset, default, nullsModifier, windowPartitionClause, windowOrderClause)

fun lag(
    expression: UnaliasedExpression<out ValidType>,
    offset: TypeExpression<NumberType>? = null,
    default: UnaliasedExpression<out ValidType>? = null,
    nullsModifier: NullsModifier? = null,
    windowReference: String,
) = Lag(expression, offset, default, nullsModifier, windowReference)
