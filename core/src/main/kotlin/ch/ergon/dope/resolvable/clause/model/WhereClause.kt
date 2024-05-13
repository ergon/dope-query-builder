package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.clause.IDeleteWhereClause
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectWhereClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.BooleanType

private const val WHERE = "WHERE"

class SelectWhereClause(private val whereExpression: TypeExpression<BooleanType>, private val parentClause: ISelectFromClause) :
    ISelectWhereClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, WHERE, whereExpression)
}

class DeleteWhereClause(private val booleanExpression: TypeExpression<BooleanType>, private val parentClause: IDeleteClause) :
    IDeleteWhereClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, WHERE, booleanExpression)
}
