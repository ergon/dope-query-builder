package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.IUpdateUnsetClause
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.ValidType

class UnsetClause(
    private val field: Field<out ValidType>,
    private vararg val fields: Field<out ValidType>,
    private val parentClause: IUpdateUnsetClause,
) : IUpdateUnsetClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery(manager)
        val fieldsDopeQuery = fields.map { it.toDopeQuery(manager) }
        val parentClauseDopeQuery = parentClause.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryString(
                "${parentClauseDopeQuery.queryString} UNSET",
                fieldDopeQuery.queryString,
                *fieldsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = parentClauseDopeQuery.parameters,
            positionalParameters = parentClauseDopeQuery.positionalParameters,
        )
    }

    fun unset(field: Field<out ValidType>) =
        UnsetClause(this.field, *this.fields, field, parentClause = this.parentClause)
}
