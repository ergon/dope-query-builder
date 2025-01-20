package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.expression.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

private const val UNNEST = "UNNEST"

class UnnestClause<T : ValidType, U : ValidType>(
    private val arrayTypeField: Field<ArrayType<T>>,
    private val parentClause: ISelectUnnestClause<U>,
) : ISelectUnnestClause<U> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val arrayTypeDopeQuery = arrayTypeField.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, UNNEST, arrayTypeDopeQuery.queryString),
            parameters = parentDopeQuery.parameters.merge(arrayTypeDopeQuery.parameters),
        )
    }
}

class AliasedUnnestClause<T : ValidType, U : ValidType>(
    private val aliasedTypeExpression: AliasedTypeExpression<ArrayType<T>>,
    private val parentClause: ISelectUnnestClause<U>,
) : ISelectUnnestClause<U> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val aliasedTypeExpressionDopeQuery = aliasedTypeExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, UNNEST, aliasedTypeExpressionDopeQuery.queryString),
            parameters = parentDopeQuery.parameters.merge(aliasedTypeExpressionDopeQuery.parameters),
        )
    }
}
