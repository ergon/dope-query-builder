package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IDeleteLimitClause
import ch.ergon.dope.resolvable.clause.IDeleteWhereClause
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatMinimumTwoToQueryString
import ch.ergon.dope.validtype.NumberType

private const val LIMIT = "LIMIT"

class SelectLimitClause(private val numberExpression: TypeExpression<NumberType>, private val parentClause: ISelectOrderByClause) :
    ISelectLimitClause {

    override fun toQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toQuery()
        val numberDopeQuery = numberExpression.toQuery()
        return DopeQuery(
            queryString = formatMinimumTwoToQueryString(parentDopeQuery.queryString, LIMIT, numberDopeQuery.queryString),
            parameters = numberDopeQuery.parameters + parentDopeQuery.parameters,
        )
    }
}

class DeleteLimitClause(private val numberExpression: TypeExpression<NumberType>, private val parentClause: IDeleteWhereClause) :
    IDeleteLimitClause {

    override fun toQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toQuery()
        val numberDopeQuery = numberExpression.toQuery()
        return DopeQuery(
            queryString = formatMinimumTwoToQueryString(parentDopeQuery.queryString, LIMIT, numberDopeQuery.queryString),
            parameters = numberDopeQuery.parameters + parentDopeQuery.parameters,
        )
    }
}
