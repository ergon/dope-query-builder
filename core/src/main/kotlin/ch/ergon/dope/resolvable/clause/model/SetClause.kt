package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IUpdateSetClause
import ch.ergon.dope.resolvable.clause.IUpdateUseKeysClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.ValidType

class SetClause(
    private val fieldAssignment: Pair<Field<out ValidType>, TypeExpression<out ValidType>>,
    private vararg val fieldAssignments: Pair<Field<out ValidType>, TypeExpression<out ValidType>>,
    private val parentClause: IUpdateUseKeysClause,
) : IUpdateSetClause {
    private fun Pair<Field<out ValidType>, TypeExpression<out ValidType>>.toDopeQuery(): DopeQuery {
        val fieldDopeQuery = first.toDopeQuery()
        val valueDopeQuery = second.toDopeQuery()
        return DopeQuery(
            queryString = "${fieldDopeQuery.queryString} = ${valueDopeQuery.queryString}",
            parameters = fieldDopeQuery.parameters + valueDopeQuery.parameters,
        )
    }

    override fun toDopeQuery(): DopeQuery {
        val parentClauseDopeQuery = parentClause.toDopeQuery()
        val fieldAssignmentDopeQuery = fieldAssignment.toDopeQuery()
        val fieldAssignmentsDopeQuery = fieldAssignments.map { it.toDopeQuery() }
        return DopeQuery(
            queryString = formatToQueryString(
                "${parentClauseDopeQuery.queryString} SET",
                fieldAssignmentDopeQuery.queryString,
                *fieldAssignmentsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = fieldAssignmentsDopeQuery.fold(fieldAssignmentDopeQuery.parameters) {
                    setExpressionParameters, field ->
                setExpressionParameters + field.parameters
            } + parentClauseDopeQuery.parameters,
        )
    }
}
