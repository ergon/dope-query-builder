package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.IDeleteLimitClause
import ch.ergon.dope.resolvable.clause.IDeleteWhereClause
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.clause.IUpdateLimitClause
import ch.ergon.dope.resolvable.clause.IUpdateWhereClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.util.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

sealed class LimitClause(
    private val numberExpression: TypeExpression<NumberType>,
    private val parentClause: Clause,
) {
    fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val numberDopeQuery = numberExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, "LIMIT", numberDopeQuery.queryString),
            parameters = parentDopeQuery.parameters.merge(numberDopeQuery.parameters),
        )
    }
}

class SelectLimitClause<T : ValidType>(numberExpression: TypeExpression<NumberType>, parentClause: ISelectOrderByClause<T>) :
    ISelectLimitClause<T>, LimitClause(numberExpression, parentClause)

class DeleteLimitClause(numberExpression: TypeExpression<NumberType>, parentClause: IDeleteWhereClause) :
    IDeleteLimitClause, LimitClause(numberExpression, parentClause)

class UpdateLimitClause(numberExpression: TypeExpression<NumberType>, parentClause: IUpdateWhereClause) :
    IUpdateLimitClause, LimitClause(numberExpression, parentClause)
