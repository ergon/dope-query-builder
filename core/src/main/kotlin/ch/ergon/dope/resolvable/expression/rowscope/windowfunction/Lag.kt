package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val LAG = "LAG"

class Lag<T : ValidType> : WindowFunction<T> {
    constructor(
        expression: TypeExpression<T>,
        offset: TypeExpression<NumberType>? = null,
        default: TypeExpression<T>? = null,
        nullsModifier: NullsModifier? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>,
    ) : super(
        functionName = LAG,
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
        functionName = LAG,
        windowFunctionArguments = WindowFunctionArguments(expression, offset, default),
        nullsModifier = nullsModifier,
        overClause = OverClauseWindowReference(windowReference),
    )
}

fun <T : ValidType> lag(
    expression: TypeExpression<T>,
    offset: TypeExpression<NumberType>? = null,
    default: TypeExpression<T>? = null,
    nullsModifier: NullsModifier? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>,
) = Lag(expression, offset, default, nullsModifier, windowPartitionClause, windowOrderClause)

fun <T : ValidType> lag(
    expression: TypeExpression<T>,
    offset: TypeExpression<NumberType>? = null,
    default: TypeExpression<T>? = null,
    nullsModifier: NullsModifier? = null,
    windowReference: String,
) = Lag(expression, offset, default, nullsModifier, windowReference)
