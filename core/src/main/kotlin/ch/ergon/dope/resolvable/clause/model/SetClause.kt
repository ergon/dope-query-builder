package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IUpdateSetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class SetClause(
    private val fieldAssignment: SetAssignment<out ValidType>,
    private vararg val fieldAssignments: SetAssignment<out ValidType>,
    private val parentClause: IUpdateSetClause,
) : IUpdateSetClause {
    override fun toDopeQuery(): DopeQuery {
        val fieldAssignmentDopeQuery = fieldAssignment.toDopeQuery()
        val fieldAssignmentsDopeQuery = fieldAssignments.map { it.toDopeQuery() }
        val parentClauseDopeQuery = parentClause.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryString(
                "${parentClauseDopeQuery.queryString} SET",
                fieldAssignmentDopeQuery.queryString,
                *fieldAssignmentsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = fieldAssignmentsDopeQuery.fold(fieldAssignmentDopeQuery.parameters) {
                    setAssignmentsParameters, field ->
                setAssignmentsParameters + field.parameters
            } + parentClauseDopeQuery.parameters,
        )
    }

    fun <T : ValidType> set(field: Field<T>, value: TypeExpression<T>) =
        SetClause(this.fieldAssignment, *this.fieldAssignments, field.to(value), parentClause = this.parentClause)

    fun set(field: Field<NumberType>, value: Number) =
        SetClause(
            this.fieldAssignment,
            *this.fieldAssignments,
            field.to(value.toDopeType()),
            parentClause = this.parentClause,
        )

    fun set(field: Field<StringType>, value: String) =
        SetClause(
            this.fieldAssignment,
            *this.fieldAssignments,
            field.to(value.toDopeType()),
            parentClause = this.parentClause,
        )

    fun set(field: Field<BooleanType>, value: Boolean) =
        SetClause(
            this.fieldAssignment,
            *this.fieldAssignments,
            field.to(value.toDopeType()),
            parentClause = this.parentClause,
        )
}

class SetAssignment<T : ValidType>(
    private val field: Field<T>,
    private val value: TypeExpression<T>,
) {
    fun toDopeQuery(): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery()
        val valueDopeQuery = value.toDopeQuery()
        return DopeQuery(
            queryString = "${fieldDopeQuery.queryString} = ${valueDopeQuery.queryString}",
            parameters = fieldDopeQuery.parameters + valueDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> Field<T>.to(value: TypeExpression<T>) = SetAssignment(this, value)
