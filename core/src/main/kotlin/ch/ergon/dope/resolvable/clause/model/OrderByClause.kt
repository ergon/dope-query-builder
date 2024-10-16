package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

enum class OrderByType(val type: String) {
    ASC("ASC"),
    DESC("DESC"),
}

private const val ORDER_BY = "ORDER BY"

open class SelectOrderByClause<T : ValidType>(private val stringField: Field<StringType>, private val parentClause: ISelectGroupByClause<T>) :
    ISelectOrderByClause<T> {

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val stringDopeQuery = stringField.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, ORDER_BY, stringDopeQuery.queryString),
            parameters = parentDopeQuery.parameters.merge(stringDopeQuery.parameters),
        )
    }
}

class SelectOrderByTypeClause<T : ValidType>(
    private val stringField: Field<StringType>,
    private val orderByType: OrderByType,
    private val parentClause: ISelectGroupByClause<T>,
) : SelectOrderByClause<T>(stringField, parentClause) {

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val stringDopeQuery = stringField.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                parentDopeQuery.queryString,
                ORDER_BY,
                stringDopeQuery.queryString + " $orderByType",
            ),
            parameters = parentDopeQuery.parameters.merge(stringDopeQuery.parameters),
        )
    }
}
