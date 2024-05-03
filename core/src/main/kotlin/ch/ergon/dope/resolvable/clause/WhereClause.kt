package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.BooleanType

private const val WHERE = "WHERE"

class WhereClause(private val whereExpression: TypeExpression<BooleanType>, private val parentClause: IFromClause) : IWhereClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, WHERE, whereExpression)
}

class DeleteWhereClause(private val booleanExpression: TypeExpression<BooleanType>, private val parent: IDeleteClause) : IDeleteWhereClause {
    override fun toQueryString(): String {
        return formatToQueryString(parent, WHERE, booleanExpression)
    }
}
