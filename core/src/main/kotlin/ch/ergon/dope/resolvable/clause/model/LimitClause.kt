package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IDeleteLimitClause
import ch.ergon.dope.resolvable.clause.IDeleteWhereClause
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.NumberType

private const val LIMIT = "LIMIT"

class SelectLimitClause(private val numberExpression: TypeExpression<NumberType>, private val parentClause: ISelectOrderByClause) :
    ISelectLimitClause {

    override fun toDopeQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery()
        val numberDopeQuery = numberExpression.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, LIMIT, numberDopeQuery.queryString),
            parameters = numberDopeQuery.parameters + parentDopeQuery.parameters,
        )
    }
}

class DeleteLimitClause(private val numberExpression: TypeExpression<NumberType>, private val parentClause: IDeleteWhereClause) :
    IDeleteLimitClause {

    override fun toDopeQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery()
        val numberDopeQuery = numberExpression.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, LIMIT, numberDopeQuery.queryString),
            parameters = numberDopeQuery.parameters + parentDopeQuery.parameters,
        )
    }
}
