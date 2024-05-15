package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.clause.IDeleteWhereClause
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectWhereClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatMinimumTwoToQueryString
import ch.ergon.dope.validtype.BooleanType

private const val WHERE = "WHERE"

class SelectWhereClause(private val whereExpression: TypeExpression<BooleanType>, private val parentClause: ISelectFromClause) :
    ISelectWhereClause {
    override fun toDopeQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery()
        val whereDopeQuery = whereExpression.toDopeQuery()
        return DopeQuery(
            queryString = formatMinimumTwoToQueryString(parentDopeQuery.queryString, WHERE, whereDopeQuery.queryString),
            parameters = whereDopeQuery.parameters + parentDopeQuery.parameters,
        )
    }
}

class DeleteWhereClause(private val booleanExpression: TypeExpression<BooleanType>, private val parentClause: IDeleteClause) :
    IDeleteWhereClause {
    override fun toDopeQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery()
        val booleanDopeQuery = booleanExpression.toDopeQuery()
        return DopeQuery(
            queryString = formatMinimumTwoToQueryString(parentDopeQuery.queryString, WHERE, booleanDopeQuery.queryString),
            parameters = booleanDopeQuery.parameters + parentDopeQuery.parameters,
        )
    }
}
