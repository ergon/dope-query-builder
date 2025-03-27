package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val LEAD = "LEAD"

class Lead<T : ValidType> : WindowFunctionExpression<T> {
    constructor(
        expression: TypeExpression<T>,
        offset: TypeExpression<NumberType>? = null,
        default: TypeExpression<T>? = null,
        nullsModifier: NullsModifier? = null,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>,
    ) : super(
        functionName = LEAD,
        functionArguments = listOf(expression, offset, default),
        nullsModifier = nullsModifier,
        overDefinition = OverWindowDefinition(
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
        functionArguments = listOf(expression, offset, default),
        nullsModifier = nullsModifier,
        overDefinition = OverWindowReference(windowReference),
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
