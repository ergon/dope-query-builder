package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

private const val LAST_VALUE = "LAST_VALUE"

class LastValue<T : ValidType> : WindowFunctionExpression<T> {
    constructor(
        expression: TypeExpression<T>,
        nullsModifier: NullsModifier? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ) : super(
        functionName = LAST_VALUE,
        functionArguments = listOf(expression),
        nullsModifier = nullsModifier,
        overDefinition = OverWindowDefinition(
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
        windowReference: String,
    ) : super(
        functionName = LAST_VALUE,
        functionArguments = listOf(expression),
        nullsModifier = nullsModifier,
        overDefinition = OverWindowReference(windowReference),
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
    windowReference: String,
) = LastValue(expression, nullsModifier, windowReference)
