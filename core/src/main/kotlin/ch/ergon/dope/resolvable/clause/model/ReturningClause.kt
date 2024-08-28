package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.IDeleteOffsetClause
import ch.ergon.dope.resolvable.clause.IDeleteReturningClause
import ch.ergon.dope.resolvable.clause.IUpdateLimitClause
import ch.ergon.dope.resolvable.clause.IUpdateReturningClause
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ValidType

sealed class ReturningClause(
    private val field: Field<out ValidType>,
    private vararg val fields: Field<out ValidType>,
    private val parentClause: Clause,
) {
    fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldsDopeQuery = fields.map { it.toDopeQuery(manager) }
        val fieldDopeQuery = field.toDopeQuery(manager)
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                parentDopeQuery.queryString,
                "RETURNING",
                fieldDopeQuery.queryString,
                *fieldsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = fieldsDopeQuery.fold(fieldDopeQuery.parameters) { fieldParameters, field ->
                fieldParameters + field.parameters
            } + parentDopeQuery.parameters,
        )
    }
}

class DeleteReturningClause(
    field: Field<out ValidType>,
    vararg fields: Field<out ValidType>,
    parentClause: IDeleteOffsetClause,
) : IDeleteReturningClause, ReturningClause(field, *fields, parentClause = parentClause)

class UpdateReturningClause(
    field: Field<out ValidType>,
    vararg fields: Field<out ValidType>,
    parentClause: IUpdateLimitClause,
) : IUpdateReturningClause, ReturningClause(field, *fields, parentClause = parentClause)
