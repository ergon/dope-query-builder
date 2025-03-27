package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.rowscope.RowScopeExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverDefinition
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

enum class FromModifier(val queryString: String) {
    FIRST("FROM FIRST"),
    LAST("FROM LAST"),
}

enum class NullsModifier(val queryString: String) {
    RESPECT("RESPECT NULLS"),
    IGNORE("IGNORE NULLS"),
}

sealed class WindowFunctionExpression<T : ValidType>(
    override val functionName: String,
    override val functionArguments: List<TypeExpression<out ValidType>?>? = null,
    override val fromModifier: FromModifier? = null,
    override val nullsModifier: NullsModifier? = null,
    override val overDefinition: OverDefinition,
) : RowScopeExpression<T> {
    override val quantifier: AggregateQuantifier? = null
}
