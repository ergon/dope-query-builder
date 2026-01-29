package ch.ergon.dope.couchbase.resolver

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.functionName
import ch.ergon.dope.couchbase.util.formatListToQueryStringWithBrackets
import ch.ergon.dope.couchbase.util.formatToQueryStringWithSymbol
import ch.ergon.dope.merge
import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.AliasedSelectClauseDefinition
import ch.ergon.dope.resolvable.Asterisk
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.rowscope.AliasedRowScopeExpression
import ch.ergon.dope.resolvable.expression.rowscope.RowScopeExpression
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.type.TypeExpression

interface ExpressionResolver : TypeExpressionResolver {
    fun resolve(expression: Expression<*>): CouchbaseDopeQuery = when (expression) {
        is TypeExpression<*> -> resolve(expression)

        is AliasedBucket -> CouchbaseDopeQuery("`${expression.alias}`")

        is Bucket -> CouchbaseDopeQuery("`${expression.name}`")

        is AliasedTypeExpression<*> -> {
            val inner = expression.typeExpression.toDopeQuery(this)
            CouchbaseDopeQuery(
                queryString = formatToQueryStringWithSymbol(inner.queryString, "AS", "`${expression.alias}`"),
                parameters = inner.parameters,
            )
        }

        is RowScopeExpression<*> -> {
            val argumentsDopeQuery = expression.functionArguments.mapNotNull { it?.toDopeQuery(this) }
            val over = expression.overDefinition?.toDopeQuery(this)
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
            CouchbaseDopeQuery(
                functionCallQueryString,
                argumentsDopeQuery.map { it.parameters }.merge(over?.parameters),
            )
        }

        is AliasedRowScopeExpression<*> -> {
            val inner = expression.rowScopeExpression.toDopeQuery(this)
            CouchbaseDopeQuery(
                formatToQueryStringWithSymbol(inner.queryString, "AS", "`${expression.alias}`"),
                inner.parameters,
            )
        }

        is AliasedSelectClause<*> -> CouchbaseDopeQuery("`${expression.alias}`")

        is AliasedSelectClauseDefinition<*> -> {
            val parent = expression.parentClause.toDopeQuery(this)
            CouchbaseDopeQuery("(${parent.queryString}) AS `${expression.alias}`", parent.parameters)
        }

        else -> throw UnsupportedOperationException("Unsupported expression type: ${expression.javaClass.name}")
    }

    fun resolve(asterisk: Asterisk): CouchbaseDopeQuery {
        val path = asterisk.path?.toDopeQuery(this)?.queryString
        return CouchbaseDopeQuery(queryString = path?.let { "$it.*" } ?: "*")
    }
}
