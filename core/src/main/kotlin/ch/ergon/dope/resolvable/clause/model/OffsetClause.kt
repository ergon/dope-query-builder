package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IDeleteLimitClass
import ch.ergon.dope.resolvable.clause.IDeleteOffsetClass
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatMinimumTwoToQueryString
import ch.ergon.dope.validtype.NumberType

private const val OFFSET = "OFFSET"

class SelectOffsetClause(private val numberExpression: TypeExpression<NumberType>, private val parentClause: ISelectLimitClause) :
    ISelectOffsetClause {

    override fun toQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toQuery()
        val numberDopeQuery = numberExpression.toQuery()
        return DopeQuery(
            queryString = formatMinimumTwoToQueryString(parentDopeQuery.queryString, OFFSET, numberDopeQuery.queryString),
            parameters = numberDopeQuery.parameters + parentDopeQuery.parameters,
        )
    }
}

class DeleteOffsetClause(private val numberExpression: TypeExpression<NumberType>, private val parentClause: IDeleteLimitClass) :
    IDeleteOffsetClass {

    override fun toQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toQuery()
        val numberDopeQuery = numberExpression.toQuery()
        return DopeQuery(
            queryString = formatMinimumTwoToQueryString(parentDopeQuery.queryString, OFFSET, numberDopeQuery.queryString),
            parameters = numberDopeQuery.parameters + parentDopeQuery.parameters,
        )
    }
}
