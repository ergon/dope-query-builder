package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.IDeleteLimitClass
import ch.ergon.dope.resolvable.clause.IDeleteOffsetClass
import ch.ergon.dope.resolvable.clause.ISelectLimitClause
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.NumberType

private const val OFFSET = "OFFSET"

class SelectOffsetClause(private val numberExpression: TypeExpression<NumberType>, private val parentClause: ISelectLimitClause) :
    ISelectOffsetClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, OFFSET, numberExpression)
}

class DeleteOffsetClause(private val numberExpression: TypeExpression<NumberType>, private val parentClause: IDeleteLimitClass) :
    IDeleteOffsetClass {
    override fun toQueryString(): String = formatToQueryString(parentClause, OFFSET, numberExpression)
}
