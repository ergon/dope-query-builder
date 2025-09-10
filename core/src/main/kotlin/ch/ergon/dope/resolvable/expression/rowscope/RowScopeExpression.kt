package ch.ergon.dope.resolvable.expression.rowscope

import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.FromModifier
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier
import ch.ergon.dope.validtype.ValidType

interface RowScopeExpression<T : ValidType> : Expression<T> {
    val functionName: String
    val quantifier: AggregateQuantifier?
    val functionArguments: List<Selectable?>
    val fromModifier: FromModifier?
    val nullsModifier: NullsModifier?
    val overDefinition: OverDefinition?
}
