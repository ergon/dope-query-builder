package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectGroupByClause
import ch.ergon.dope.resolvable.clause.ISelectWhereClause
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ValidType

class GroupByClause(
    private val field: Field<out ValidType>,
    private vararg val fields: Field<out ValidType>,
    private val parentClause: ISelectWhereClause,
) : ISelectGroupByClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val fieldDopeQuery = field.toDopeQuery(manager)
        val fieldsDopeQuery = fields.map { it.toDopeQuery(manager) }
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                parentDopeQuery.queryString,
                "GROUP BY",
                fieldDopeQuery.queryString,
                *fieldsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = fieldsDopeQuery.fold(fieldDopeQuery.parameters) { fieldParameters, field ->
                fieldParameters + field.parameters
            } + parentDopeQuery.parameters,
            positionalParameters = parentDopeQuery.positionalParameters + fieldsDopeQuery.fold(
                fieldDopeQuery.positionalParameters,
            ) { fieldParameters, field -> fieldParameters + field.positionalParameters },
        )
    }
}
