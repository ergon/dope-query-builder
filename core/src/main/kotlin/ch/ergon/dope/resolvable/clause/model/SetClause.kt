package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.IUpdateClause
import ch.ergon.dope.resolvable.clause.IUpdateSetClause
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.util.formatToQueryString
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class SetClause(
    private val setAssignment: SetAssignment<out ValidType>,
    private vararg val setAssignments: SetAssignment<out ValidType>,
    private val parentClause: IUpdateClause,
) : IUpdateSetClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val setAssignmentDopeQuery = setAssignment.toDopeQuery(manager)
        val setAssignmentsDopeQuery = setAssignments.map { it.toDopeQuery(manager) }
        val parentClauseDopeQuery = parentClause.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryString(
                "${parentClauseDopeQuery.queryString} SET",
                setAssignmentDopeQuery.queryString,
                *setAssignmentsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = parentClauseDopeQuery.parameters.merge(
                setAssignmentDopeQuery.parameters,
                *setAssignmentsDopeQuery.map { it.parameters }.toTypedArray(),
            ),
        )
    }
}

class SetAssignment<T : ValidType>(
    private val field: Field<T>,
    private val value: TypeExpression<out T>,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery(manager)
        val valueDopeQuery = value.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${fieldDopeQuery.queryString} = ${valueDopeQuery.queryString}",
            parameters = fieldDopeQuery.parameters.merge(valueDopeQuery.parameters),
        )
    }
}

infix fun <T : ValidType> Field<T>.to(value: TypeExpression<out T>) = SetAssignment(this, value)

infix fun Field<NumberType>.to(value: Number) = to(value.toDopeType())

infix fun Field<StringType>.to(value: String) = to(value.toDopeType())

infix fun Field<BooleanType>.to(value: Boolean) = to(value.toDopeType())
