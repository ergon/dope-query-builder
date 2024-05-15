package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectWhereClause
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatMinimumTwoToQueryString
import ch.ergon.dope.validtype.ValidType

class GroupByClause(
    private val field: Field<out ValidType>,
    private vararg val fields: Field<out ValidType>,
    private val parentClause: ISelectWhereClause,
) : ISelectGroupByClause {

    override fun toDopeQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery()
        val fieldDopeQuery = field.toDopeQuery()
        val fieldsDopeQuery = fields.map { it.toDopeQuery() }
        return DopeQuery(
            queryString = formatMinimumTwoToQueryString(
                parentDopeQuery.queryString,
                "GROUP BY",
                fieldDopeQuery.queryString,
                *fieldsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = fieldDopeQuery.parameters + fieldsDopeQuery.fold(emptyMap()) { fieldParameters, field ->
                fieldParameters + field.parameters
            } + parentDopeQuery.parameters,
        )
    }
}
