package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.expression.AliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatMinimumTwoToQueryString
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

private const val UNNEST = "UNNEST"

class UnnestClause<T : ValidType>(private val arrayTypeField: Field<ArrayType<T>>, private val parentClause: ISelectUnnestClause) :
    ISelectUnnestClause {
    override fun toQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toQuery()
        val arrayTypeDopeQuery = arrayTypeField.toQuery()
        return DopeQuery(
            queryString = formatMinimumTwoToQueryString(parentDopeQuery.queryString, UNNEST, arrayTypeDopeQuery.queryString),
            parameters = parentDopeQuery.parameters + arrayTypeDopeQuery.parameters,
        )
    }
}

class AliasedUnnestClause<T : ValidType>(
    private val aliasedExpression: AliasedExpression<ArrayType<T>>,
    private val parentClause: ISelectUnnestClause,
) : ISelectUnnestClause {
    override fun toQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toQuery()
        val aliasedExpressionDopeQuery = aliasedExpression.toQuery()
        return DopeQuery(
            queryString = formatMinimumTwoToQueryString(parentDopeQuery.queryString, UNNEST, aliasedExpressionDopeQuery.queryString),
            parameters = parentDopeQuery.parameters + aliasedExpressionDopeQuery.parameters,
        )
    }
}
