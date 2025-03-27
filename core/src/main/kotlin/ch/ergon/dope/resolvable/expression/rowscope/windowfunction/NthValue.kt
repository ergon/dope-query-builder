package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val NTH_VALUE = "NTH_VALUE"

class NthValue<T : ValidType> : WindowFunctionExpression<T> {
    constructor(
        expression: TypeExpression<T>,
        offset: TypeExpression<NumberType>,
        nullsModifier: NullsModifier? = null,
        fromModifier: FromModifier? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ) : super(
        functionName = NTH_VALUE,
        functionArguments = listOf(expression, offset),
        fromModifier = fromModifier,
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
        offset: TypeExpression<NumberType>,
        nullsModifier: NullsModifier? = null,
        fromModifier: FromModifier? = null,
        windowReference: String,
    ) : super(
        functionName = NTH_VALUE,
        functionArguments = listOf(expression, offset),
        fromModifier = fromModifier,
        nullsModifier = nullsModifier,
        overDefinition = OverWindowReference(windowReference),
    )
}

fun <T : ValidType> nthValue(
    expression: TypeExpression<T>,
    offset: TypeExpression<NumberType>,
    nullsModifier: NullsModifier? = null,
    fromModifier: FromModifier? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
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

fun <T : ValidType> nthValue(
    expression: TypeExpression<T>,
    offset: Number,
    nullsModifier: NullsModifier? = null,
    fromModifier: FromModifier? = null,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = nthValue(
    expression,
    offset.toDopeType(),
    nullsModifier,
    fromModifier,
    windowPartitionClause,
    windowOrderClause,
    windowFrameClause,
)

fun <T : ValidType> nthValue(
    expression: TypeExpression<T>,
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

fun <T : ValidType> nthValue(
    expression: TypeExpression<T>,
    offset: Number,
    nullsModifier: NullsModifier? = null,
    fromModifier: FromModifier? = null,
    windowReference: String,
) = nthValue(
    expression,
    offset.toDopeType(),
    nullsModifier,
    fromModifier,
    windowReference,
)
