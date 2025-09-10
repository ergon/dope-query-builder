package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.rowscope.RowScopeExpression
import ch.ergon.dope.validtype.ValidType

enum class FromModifier(val queryString: String) {
    FIRST("FROM FIRST"),
    LAST("FROM LAST"),
}

enum class NullsModifier(val queryString: String) {
    RESPECT("RESPECT NULLS"),
    IGNORE("IGNORE NULLS"),
}

sealed interface WindowFunctionExpression<T : ValidType> : RowScopeExpression<T>
