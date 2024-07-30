package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IDeleteUseKeysClause
import ch.ergon.dope.resolvable.clause.IDeleteWhereClause
import ch.ergon.dope.resolvable.clause.ISelectUseKeysClause
import ch.ergon.dope.resolvable.clause.ISelectWhereClause
import ch.ergon.dope.resolvable.clause.IUpdateUnsetClause
import ch.ergon.dope.resolvable.clause.IUpdateWhereClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.BooleanType

private const val WHERE = "WHERE"

class SelectWhereClause(private val whereExpression: TypeExpression<BooleanType>, private val parentClause: ISelectUseKeysClause) :
    ISelectWhereClause {
    override fun toDopeQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery()
        val whereDopeQuery = whereExpression.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, WHERE, whereDopeQuery.queryString),
            parameters = whereDopeQuery.parameters + parentDopeQuery.parameters,
        )
    }
}

class DeleteWhereClause(private val booleanExpression: TypeExpression<BooleanType>, private val parentClause: IDeleteUseKeysClause) :
    IDeleteWhereClause {
    override fun toDopeQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery()
        val booleanDopeQuery = booleanExpression.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, WHERE, booleanDopeQuery.queryString),
            parameters = booleanDopeQuery.parameters + parentDopeQuery.parameters,
        )
    }
}

class UpdateWhereClause(private val booleanExpression: TypeExpression<BooleanType>, private val parentClause: IUpdateUnsetClause) :
    IUpdateWhereClause {
    override fun toDopeQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery()
        val booleanDopeQuery = booleanExpression.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, WHERE, booleanDopeQuery.queryString),
            parameters = booleanDopeQuery.parameters + parentDopeQuery.parameters,
        )
    }
}
