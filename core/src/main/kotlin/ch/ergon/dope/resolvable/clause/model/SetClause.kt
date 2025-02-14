package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.IUpdateSetClause
import ch.ergon.dope.resolvable.expression.single.type.Field
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.util.formatToQueryString
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class SetClause(
    private val fieldAssignment: SetAssignment<out ValidType>,
    private vararg val fieldAssignments: SetAssignment<out ValidType>,
    private val parentClause: IUpdateSetClause,
) : IUpdateSetClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldAssignmentDopeQuery = fieldAssignment.toDopeQuery(manager)
        val fieldAssignmentsDopeQuery = fieldAssignments.map { it.toDopeQuery(manager) }
        val parentClauseDopeQuery = parentClause.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryString(
                "${parentClauseDopeQuery.queryString} SET",
                fieldAssignmentDopeQuery.queryString,
                *fieldAssignmentsDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = parentClauseDopeQuery.parameters.merge(
                fieldAssignmentDopeQuery.parameters,
                *fieldAssignmentsDopeQuery.map { it.parameters }.toTypedArray(),
            ),
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

fun <T : ValidType> Field<T>.to(value: TypeExpression<T>) = SetAssignment(this, value)
