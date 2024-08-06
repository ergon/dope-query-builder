package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IUpdateUnsetClause
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.ValidType

class UnsetClause(
    field: Field<out ValidType>,
    private val fields: MutableList<Field<out ValidType>> = mutableListOf(),
    private val parentClause: IUpdateUnsetClause,
) : IUpdateUnsetClause {

    init {
        fields.add(field)
    }

    override fun toDopeQuery(): DopeQuery {
        val fieldsDopeQuery = fields.map { it.toDopeQuery() }
        val parentClauseDopeQuery = parentClause.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryString(
                "${parentClauseDopeQuery.queryString} UNSET",
                *fieldsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = parentClauseDopeQuery.parameters,
        )
    }

    fun unset(field: Field<out ValidType>) = UnsetClause(field, fields = this.fields, parentClause = this.parentClause)
}
