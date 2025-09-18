package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.clause.ISelectWindowClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

enum class OrderType {
    ASC,
    DESC,
}

data class SelectOrderByClause<T : ValidType>(
    val orderExpression: OrderExpression,
    val additionalOrderExpressions: List<OrderExpression> = emptyList(),
    val parentClause: ISelectWindowClause<T>,
) : ISelectOrderByClause<T> {

    fun thenOrderBy(orderExpression: OrderExpression) =
        SelectOrderByClause(
            this.orderExpression,
            additionalOrderExpressions + orderExpression,
            parentClause = this.parentClause,
        )

    fun thenOrderBy(typeExpression: TypeExpression<out ValidType>, orderByType: OrderType? = null) =
        thenOrderBy(OrderExpression(typeExpression, orderByType))
}

data class OrderExpression(
    val expression: TypeExpression<out ValidType>,
    val orderByType: OrderType? = null,
) : Resolvable
