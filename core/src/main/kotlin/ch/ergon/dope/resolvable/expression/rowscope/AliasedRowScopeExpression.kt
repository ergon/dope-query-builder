package ch.ergon.dope.resolvable.expression.rowscope

import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.validtype.ValidType

data class AliasedRowScopeExpression<T : ValidType>(
    val rowScopeExpression: RowScopeExpression<T>,
    val alias: String,
) : Expression<T>

fun <T : ValidType> RowScopeExpression<T>.alias(alias: String) = AliasedRowScopeExpression(this, alias)
