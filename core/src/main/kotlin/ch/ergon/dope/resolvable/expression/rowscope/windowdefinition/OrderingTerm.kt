package ch.ergon.dope.resolvable.expression.rowscope.windowdefinition

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.validtype.ValidType

enum class NullsOrder {
    NULLS_FIRST,
    NULLS_LAST,
}

data class OrderingTerm(
    val expression: Expression<out ValidType>,
    val orderType: OrderType? = null,
    val nullsOrder: NullsOrder? = null,
) : Resolvable

fun orderingTerm(
    expression: Expression<out ValidType>,
    orderType: OrderType? = null,
    nullsOrder: NullsOrder? = null,
): OrderingTerm = OrderingTerm(expression, orderType, nullsOrder)
