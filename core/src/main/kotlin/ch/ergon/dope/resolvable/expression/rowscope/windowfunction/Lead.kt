package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val LEAD = "LEAD"

class Lead<T : ValidType> : WindowFunction<T> {
    constructor(
        expression: TypeExpression<T>,
        offset: TypeExpression<NumberType>? = null,
        default: TypeExpression<T>? = null,
        nullsModifier: NullsModifier? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>,
    ) : super(
        functionName = LEAD,
        windowFunctionArguments = WindowFunctionArguments(expression, offset, default),
        nullsModifier = nullsModifier,
        overClause = OverClauseWindowDefinition(
            WindowDefinition(
                windowPartitionClause = windowPartitionClause,
                windowOrderClause = windowOrderClause,
            ),
        ),
    )

    constructor(
        expression: TypeExpression<T>,
        offset: TypeExpression<NumberType>? = null,
        default: TypeExpression<T>? = null,
        nullsModifier: NullsModifier? = null,
        windowReference: String,
    ) : super(
        functionName = LEAD,
        windowFunctionArguments = WindowFunctionArguments(expression, offset, default),
        nullsModifier = nullsModifier,
        overClause = OverClauseWindowReference(windowReference),
    )
}

fun <T : ValidType> lead(
    expression: TypeExpression<T>,
    offset: TypeExpression<NumberType>? = null,
    default: TypeExpression<T>? = null,
    nullsModifier: NullsModifier? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>,
) = Lead(expression, offset, default, nullsModifier, windowPartitionClause, windowOrderClause)

fun <T : ValidType> lead(
    expression: TypeExpression<T>,
    offset: TypeExpression<NumberType>? = null,
    default: TypeExpression<T>? = null,
    nullsModifier: NullsModifier? = null,
    windowReference: String,
) = Lead(expression, offset, default, nullsModifier, windowReference)
