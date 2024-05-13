package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.IDeleteLimitClause
import ch.ergon.dope.resolvable.clause.IDeleteWhereClause
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOrderByClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.NumberType

private const val LIMIT = "LIMIT"

class SelectLimitClause(private val numberExpression: TypeExpression<NumberType>, private val parentClause: ISelectOrderByClause) :
    ISelectLimitClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, LIMIT, numberExpression)
}

class DeleteLimitClause(private val numberExpression: TypeExpression<NumberType>, private val parentClause: IDeleteWhereClause) :
    IDeleteLimitClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, LIMIT, numberExpression)
}
