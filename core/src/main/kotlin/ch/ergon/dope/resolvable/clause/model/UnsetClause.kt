package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IUpdateSetClause
import ch.ergon.dope.resolvable.clause.IUpdateUnsetClause
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.ValidType

class UnsetClause(
    private val field: Field<out ValidType>,
    private vararg val fields: Field<out ValidType>,
    private val parentClause: IUpdateSetClause,
) : IUpdateUnsetClause {
    override fun toDopeQuery(): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery()
        val fieldsDopeQuery = fields.map { it.toDopeQuery() }
        val parentClauseDopeQuery = parentClause.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryString(
                "${parentClauseDopeQuery.queryString} UNSET",
                fieldDopeQuery.queryString,
                *fieldsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = fieldsDopeQuery.fold(fieldDopeQuery.parameters) {
                    fieldParameters, field ->
                fieldParameters + field.parameters
            } + parentClauseDopeQuery.parameters,
        )
    }
}
