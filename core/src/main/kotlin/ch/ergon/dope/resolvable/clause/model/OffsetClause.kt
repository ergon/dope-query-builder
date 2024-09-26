package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.IDeleteLimitClause
import ch.ergon.dope.resolvable.clause.IDeleteOffsetClause
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.NumberType

sealed class OffsetClause(
    private val numberExpression: TypeExpression<NumberType>,
    private val parentClause: Clause,
) {
    fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val numberDopeQuery = numberExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, "OFFSET", numberDopeQuery.queryString),
            parameters = parentDopeQuery.parameters + numberDopeQuery.parameters,
            positionalParameters = parentDopeQuery.positionalParameters + numberDopeQuery.positionalParameters,
        )
    }
}

class SelectOffsetClause(numberExpression: TypeExpression<NumberType>, parentClause: ISelectLimitClause) :
    ISelectOffsetClause, OffsetClause(numberExpression, parentClause)

class DeleteOffsetClause(numberExpression: TypeExpression<NumberType>, parentClause: IDeleteLimitClause) :
    IDeleteOffsetClause, OffsetClause(numberExpression, parentClause)
