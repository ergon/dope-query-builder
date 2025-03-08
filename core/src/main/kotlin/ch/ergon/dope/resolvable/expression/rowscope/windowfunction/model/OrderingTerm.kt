package ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.validtype.ValidType

enum class NullsOrder(val queryString: String) {
    NULLS_FIRST("NULLS FIRST"),
    NULLS_LAST("NULLS LAST"),
}

class OrderingTerm(
    private val expression: Expression<out ValidType>,
    private val orderType: OrderType? = null,
    private val nullsOrder: NullsOrder? = null,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            queryString = expressionDopeQuery.queryString +
                (orderType?.let { " $it" }.orEmpty()) +
                (nullsOrder?.let { " ${it.queryString}" }.orEmpty()),
            parameters = expressionDopeQuery.parameters,
        )
    }
}
