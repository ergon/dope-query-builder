package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val LEAD = "LEAD"

data class Lead<T : ValidType>(
    val expression: TypeExpression<T>,
    val offset: TypeExpression<NumberType>? = null,
    val default: TypeExpression<T>? = null,
    override val nullsModifier: NullsModifier? = null,
    val windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    val windowOrderClause: List<OrderingTerm>,
) : WindowFunctionExpression<T> {
    override val functionName: String = LEAD
    override val quantifier: AggregateQuantifier? = null
    override val functionArguments: List<Selectable?> = listOf(expression, offset, default)
    override val fromModifier: FromModifier? = null
    override val overDefinition: OverDefinition? = OverWindowDefinition(
        WindowDefinition(
            windowPartitionClause = windowPartitionClause,
            windowOrderClause = windowOrderClause,
        ),
    )
}

data class LeadWithReference<T : ValidType>(
    val expression: TypeExpression<T>,
    val offset: TypeExpression<NumberType>? = null,
    val default: TypeExpression<T>? = null,
    override val nullsModifier: NullsModifier? = null,
    val windowReference: String,
) : WindowFunctionExpression<T> {
    override val functionName: String = LEAD
    override val quantifier: AggregateQuantifier? = null
    override val functionArguments: List<Selectable?> = listOf(expression, offset, default)
    override val fromModifier: FromModifier? = null
    override val overDefinition: OverDefinition = OverWindowReference(windowReference)
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
) = LeadWithReference(expression, offset, default, nullsModifier, windowReference)
