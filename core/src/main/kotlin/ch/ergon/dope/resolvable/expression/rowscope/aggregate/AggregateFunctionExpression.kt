package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.expression.rowscope.RowScopeExpression
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.FromModifier
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier
import ch.ergon.dope.validtype.ValidType

sealed interface AggregateFunctionExpression<T : ValidType> : RowScopeExpression<T> {
    val selectable: Selectable
    override val functionArguments: List<Selectable>
        get() = listOf(selectable)
    override val fromModifier: FromModifier?
        get() = null
    override val nullsModifier: NullsModifier?
        get() = null
}
