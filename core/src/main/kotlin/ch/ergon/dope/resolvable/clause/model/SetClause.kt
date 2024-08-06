package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IUpdateSetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.ValidType

class SetClause<T : ValidType>(
    field: Field<T>,
    value: TypeExpression<T>,
    private val setAssignments: MutableList<SetAssignment<out ValidType>> = mutableListOf(),
    private val parentClause: IUpdateSetClause,
) : IUpdateSetClause {

    init {
        setAssignments.add(SetAssignment(field, value))
    }

    override fun toDopeQuery(): DopeQuery {
        val parentClauseDopeQuery = parentClause.toDopeQuery()
        val setAssignmentsDopeQuery = setAssignments.map { it.toDopeQuery() }
        return DopeQuery(
            queryString = formatToQueryString(
                "${parentClauseDopeQuery.queryString} SET",
                *setAssignmentsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = setAssignmentsDopeQuery.fold(parentClauseDopeQuery.parameters) {
                    setAssignmentsParameters, parent ->
                setAssignmentsParameters + parent.parameters
            },
        )
    }

    fun <T : ValidType> set(field: Field<T>, value: TypeExpression<T>) =
        SetClause(field, value, setAssignments = this.setAssignments, parentClause = this.parentClause)
}

class SetAssignment<T : ValidType>(
    private val field: Field<T>,
    private val value: TypeExpression<T>,
) : TypeExpression<T> {
    override fun toDopeQuery(): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery()
        val valueDopeQuery = value.toDopeQuery()
        return DopeQuery(
            queryString = "${fieldDopeQuery.queryString} = ${valueDopeQuery.queryString}",
            parameters = fieldDopeQuery.parameters + valueDopeQuery.parameters,
        )
    }
}
