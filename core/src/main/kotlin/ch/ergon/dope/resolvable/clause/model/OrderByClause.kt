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

class OrderExpression(private val typeExpression: TypeExpression<out ValidType>, private val orderByType: OrderByType? = null) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val typeDopeQuery = typeExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${typeDopeQuery.queryString}${orderByType?.queryString?.let { " $it" } ?: ""}",
            parameters = typeDopeQuery.parameters,
        )
    }
}

class SelectOrderByClause<T : ValidType>(
    private val orderExpressions: List<OrderExpression>,
    private val parentClause: ISelectGroupByClause<T>,
) : ISelectOrderByClause<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val stringDopeQuery = orderExpressions.map { it.toDopeQuery(manager) }
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                parentDopeQuery.queryString,
                ORDER_BY,
                *stringDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = parentDopeQuery.parameters.merge(*stringDopeQuery.map { it.parameters }.toTypedArray()),
        )
    }

    fun thenOrderBy(typeExpression: TypeExpression<out ValidType>, orderByType: OrderByType? = null) =
        SelectOrderByClause(this.orderExpressions + OrderExpression(typeExpression, orderByType), this.parentClause)
}
