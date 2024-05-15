package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IDeleteOffsetClause
import ch.ergon.dope.resolvable.clause.IDeleteReturningClause
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatMinimumTwoToQueryString
import ch.ergon.dope.validtype.ValidType

class ReturningClause(
    private val field: Field<out ValidType>,
    private vararg val fields: Field<out ValidType>,
    private val parentClause: IDeleteOffsetClause,
) : IDeleteReturningClause {

    override fun toDopeQuery(): DopeQuery {
        val fieldsDopeQuery = fields.map { it.toDopeQuery() }
        val fieldDopeQuery = field.toDopeQuery()
        val parentDopeQuery = parentClause.toDopeQuery()
        return DopeQuery(
            queryString = formatMinimumTwoToQueryString(
                parentDopeQuery.queryString,
                "RETURNING",
                fieldDopeQuery.queryString,
                *fieldsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = fieldDopeQuery.parameters + fieldsDopeQuery.fold(emptyMap()) { fieldParameters, field ->
                fieldParameters + field.parameters
            } + parentDopeQuery.parameters,
        )
    }
}
