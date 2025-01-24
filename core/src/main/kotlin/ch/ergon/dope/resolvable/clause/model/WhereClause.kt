package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.clause.IDeleteWhereClause
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectWhereClause
import ch.ergon.dope.resolvable.clause.IUpdateUnsetClause
import ch.ergon.dope.resolvable.clause.IUpdateWhereClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

sealed class WhereClause(
    private val whereExpression: TypeExpression<BooleanType>,
    private val parentClause: Clause,
) {
    fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val whereDopeQuery = whereExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, "WHERE", whereDopeQuery.queryString),
            parameters = parentDopeQuery.parameters.merge(whereDopeQuery.parameters),
        )
    }
}

class SelectWhereClause<T : ValidType>(whereExpression: TypeExpression<BooleanType>, parentClause: ISelectFromClause<T>) :
    ISelectWhereClause<T>, WhereClause(whereExpression, parentClause)

class DeleteWhereClause(whereExpression: TypeExpression<BooleanType>, parentClause: IDeleteClause) :
    IDeleteWhereClause, WhereClause(whereExpression, parentClause)

class UpdateWhereClause(whereExpression: TypeExpression<BooleanType>, parentClause: IUpdateUnsetClause) :
    IUpdateWhereClause, WhereClause(whereExpression, parentClause)
