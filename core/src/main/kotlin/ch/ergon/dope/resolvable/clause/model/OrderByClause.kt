package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ValidType

enum class OrderByType(val queryString: String) {
    ASC("ASC"),
    DESC("DESC"),
}

private const val ORDER_BY = "ORDER BY"

class SelectOrderByClause<T : ValidType>(
    private val orderExpression: OrderExpression,
    private vararg val additionalOrderExpressions: OrderExpression,
    private val parentClause: ISelectGroupByClause<T>,
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

    fun thenOrderBy(typeExpression: TypeExpression<out ValidType>, orderByType: OrderByType? = null) =
        SelectOrderByClause(
            this.orderExpression,
            *additionalOrderExpressions,
            OrderExpression(typeExpression, orderByType),
            parentClause = this.parentClause,
        )
}

class OrderExpression(private val expression: TypeExpression<out ValidType>, private val orderByType: OrderByType? = null) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val typeDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            queryString = listOfNotNull(typeDopeQuery.queryString, orderByType?.queryString).joinToString(separator = " "),
            parameters = typeDopeQuery.parameters,
        )
    }
}
