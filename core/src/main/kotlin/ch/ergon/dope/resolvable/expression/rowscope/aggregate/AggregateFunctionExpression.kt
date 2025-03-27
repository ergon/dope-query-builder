package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.expression.rowscope.RowScopeExpression
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.FromModifier
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier
import ch.ergon.dope.validtype.ValidType

sealed class AggregateFunctionExpression<T : ValidType>(
    override val functionName: String,
    val selectable: Selectable,
    override val quantifier: AggregateQuantifier?,
    override val overDefinition: OverDefinition?,
) : RowScopeExpression<T> {
    override val functionArguments: List<Selectable> = listOf(selectable)
    override val fromModifier: FromModifier? = null
    override val nullsModifier: NullsModifier? = null
}
