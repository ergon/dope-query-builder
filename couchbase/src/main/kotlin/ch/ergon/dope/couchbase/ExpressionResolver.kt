package ch.ergon.dope.couchbase

import ch.ergon.dope.couchbase.util.formatListToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatToQueryStringWithSymbol
import ch.ergon.dope.merge
import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.AliasedSelectClauseDefinition
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.rowscope.AliasedRowScopeExpression
import ch.ergon.dope.resolvable.expression.rowscope.RowScopeExpression
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression

internal object ExpressionResolver {
    fun resolve(resolver: CouchbaseResolver, expression: Expression<*>): CouchbaseDopeQuery = when (expression) {
        is AliasedTypeExpression<*> -> {
            val inner = expression.typeExpression.toDopeQuery(resolver)
            CouchbaseDopeQuery(
                queryString = formatToQueryStringWithSymbol(inner.queryString, "AS", "`${expression.alias}`"),
                parameters = inner.parameters,
            )
        }

        is RowScopeExpression<*> -> {
            val argumentsDopeQuery = expression.functionArguments.mapNotNull { it?.toDopeQuery(resolver) }
            val over = expression.overDefinition?.toDopeQuery(resolver)
            val argumentsDopeQueryString = formatListToQueryStringWithBrackets(
                argumentsDopeQuery,
                prefix = "(" + (expression.quantifier?.let { "${it.name} " } ?: ""),
            )
            val functionCallQueryString = listOfNotNull(
                expression.functionName + argumentsDopeQueryString,
                expression.fromModifier?.let { if (it.name == "FIRST") "FROM FIRST" else "FROM LAST" },
                expression.nullsModifier?.let { if (it.name == "RESPECT") "RESPECT NULLS" else "IGNORE NULLS" },
                over?.queryString,
            ).joinToString(" ")
            CouchbaseDopeQuery(functionCallQueryString, argumentsDopeQuery.map { it.parameters }.merge(over?.parameters))
        }

        is AliasedRowScopeExpression<*> -> {
            val inner = expression.rowScopeExpression.toDopeQuery(resolver)
            CouchbaseDopeQuery(formatToQueryStringWithSymbol(inner.queryString, "AS", "`${expression.alias}`"), inner.parameters)
        }

        is AliasedSelectClause<*> -> CouchbaseDopeQuery("`${expression.alias}`")

        is AliasedSelectClauseDefinition<*> -> {
            val parent = expression.parentClause.toDopeQuery(resolver)
            CouchbaseDopeQuery("(${parent.queryString}) AS `${expression.alias}`", parent.parameters)
        }

        else -> throw UnsupportedOperationException("Unsupported expression type: ${expression.javaClass.name}")
    }
}
