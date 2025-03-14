package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.clause.ISelectWindowClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.util.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ValidType

enum class OrderType(val queryString: String) {
    ASC("ASC"),
    DESC("DESC"),
}

private const val ORDER_BY = "ORDER BY"

class SelectOrderByClause<T : ValidType>(
    private val orderExpression: OrderExpression,
    private vararg val additionalOrderExpressions: OrderExpression,
    private val parentClause: ISelectWindowClause<T>,
) : ISelectOrderByClause<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val orderExpressionDopeQuery = orderExpression.toDopeQuery(manager)
        val additionalOrderExpressions = additionalOrderExpressions.map { it.toDopeQuery(manager) }
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                parentDopeQuery.queryString,
                ORDER_BY,
                orderExpressionDopeQuery.queryString,
                *additionalOrderExpressions.map { it.queryString }.toTypedArray(),
            ),
            parameters = parentDopeQuery.parameters.merge(
                orderExpressionDopeQuery.parameters,
                *additionalOrderExpressions.map { it.parameters }.toTypedArray(),
            ),
        )
    }

    fun thenOrderBy(orderExpression: OrderExpression) =
        SelectOrderByClause(
            this.orderExpression,
            *additionalOrderExpressions,
            orderExpression,
            parentClause = this.parentClause,
        )

    fun thenOrderBy(typeExpression: TypeExpression<out ValidType>, orderByType: OrderType? = null) =
        thenOrderBy(OrderExpression(typeExpression, orderByType))
}

class OrderExpression(private val expression: TypeExpression<out ValidType>, private val orderByType: OrderType? = null) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val typeDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            queryString = listOfNotNull(typeDopeQuery.queryString, orderByType?.queryString).joinToString(separator = " "),
            parameters = typeDopeQuery.parameters,
        )
    }
}
