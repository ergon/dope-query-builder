package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.expression.AliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

private const val UNNEST = "UNNEST"

class UnnestClause<T : ValidType>(private val arrayTypeField: Field<ArrayType<T>>, private val parentClause: ISelectUnnestClause) :
    ISelectUnnestClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val arrayTypeDopeQuery = arrayTypeField.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, UNNEST, arrayTypeDopeQuery.queryString),
            parameters = parentDopeQuery.parameters + arrayTypeDopeQuery.parameters,
            positionalParameters = parentDopeQuery.positionalParameters + arrayTypeDopeQuery.positionalParameters,
        )
    }
}

class AliasedUnnestClause<T : ValidType>(
    private val aliasedExpression: AliasedExpression<ArrayType<T>>,
    private val parentClause: ISelectUnnestClause,
) : ISelectUnnestClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val aliasedExpressionDopeQuery = aliasedExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, UNNEST, aliasedExpressionDopeQuery.queryString),
            parameters = parentDopeQuery.parameters + aliasedExpressionDopeQuery.parameters,
            positionalParameters = parentDopeQuery.positionalParameters + aliasedExpressionDopeQuery.positionalParameters,
        )
    }
}
