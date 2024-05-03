package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.NumberType

private const val LIMIT = "LIMIT"

class SelectLimitClause(private val numberExpression: TypeExpression<NumberType>, private val parentClause: IOrderByClause) : ILimitClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, LIMIT, numberExpression)
}

class DeleteLimitClause(private val numberExpression: TypeExpression<NumberType>, private val parentClause: IDeleteWhereClause) :
    IDeleteLimitClass {
    override fun toQueryString(): String = formatToQueryString(parentClause, LIMIT, numberExpression)
}
