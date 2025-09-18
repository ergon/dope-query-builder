package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.rowscope.RowScopeExpression
import ch.ergon.dope.validtype.ValidType

enum class FromModifier {
    FIRST,
    LAST,
}

enum class NullsModifier {
    RESPECT,
    IGNORE,
}

sealed interface WindowFunctionExpression<T : ValidType> : RowScopeExpression<T>
