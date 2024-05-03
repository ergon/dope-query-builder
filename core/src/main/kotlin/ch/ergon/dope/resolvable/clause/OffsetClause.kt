package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.NumberType

private const val OFFSET = "OFFSET"

class OffsetClause(private val numberExpression: TypeExpression<NumberType>, private val parentClause: ILimitClause) : IOffsetClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, OFFSET, numberExpression)
}

class DeleteOffsetClause(private val numberExpression: TypeExpression<NumberType>, private val parent: IDeleteLimitClass) :
    IDeleteOffsetClass {
    override fun toQueryString(): String = formatToQueryString(parent, OFFSET, numberExpression)
}
