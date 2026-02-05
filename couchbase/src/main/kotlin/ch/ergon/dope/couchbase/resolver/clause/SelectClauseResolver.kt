package ch.ergon.dope.couchbase.resolver.clause

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.util.formatQueryStringWithNullableFirst
import ch.ergon.dope.couchbase.util.formatToQueryStringWithSymbol
import ch.ergon.dope.orEmpty
import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.clause.SetOperator
import ch.ergon.dope.resolvable.clause.model.AliasedUnnestClause
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.GroupByClause
import ch.ergon.dope.resolvable.clause.model.LetClause
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.clause.model.WindowClause
import ch.ergon.dope.resolvable.clause.model.mergeable.MergeableClause
import ch.ergon.dope.resolvable.expression.type.DopeVariable
import ch.ergon.dope.resolver.QueryResolver
import ch.ergon.dope.validtype.ValidType

interface SelectClauseResolver : MergeableClauseResolver {
    fun resolve(selectClause: ISelectOffsetClause<*>): CouchbaseDopeQuery = when (selectClause) {
        is SelectClause -> {
            val parentDopeQuery = selectClause.parentClause?.toDopeQuery(this)
            val expressionDopeQuery = selectClause.expression.toDopeQuery(this)
            val expressionsDopeQuery = selectClause.expressions.map { it.toDopeQuery(this) }
            CouchbaseDopeQuery(
                queryString = formatQueryStringWithNullableFirst(
                    parentDopeQuery,
                    "SELECT",
                    expressionDopeQuery,
                    expressionsDopeQuery,
                ),
                parameters = (parentDopeQuery?.parameters.orEmpty()).merge(
                    expressionDopeQuery.parameters,
                    *expressionsDopeQuery.map { it.parameters }.toTypedArray(),
                ),
            )
        }

        is UnnestClause<*, *> -> {
            val parentDopeQuery = selectClause.parentClause.toDopeQuery(this)
            val arrayDopeQuery = selectClause.arrayTypeField.toDopeQuery(this)
            CouchbaseDopeQuery(
                queryString = formatToQueryStringWithSymbol(
                    parentDopeQuery.queryString,
                    "UNNEST",
                    arrayDopeQuery.queryString,
                ),
                parameters = parentDopeQuery.parameters.merge(arrayDopeQuery.parameters),
            )
        }

        is AliasedUnnestClause<*, *> -> {
            val parent = selectClause.parentClause.toDopeQuery(this)
            val aliased = selectClause.aliasedTypeExpression.toDopeQuery(this)
            CouchbaseDopeQuery(
                queryString = formatToQueryStringWithSymbol(parent.queryString, "UNNEST", aliased.queryString),
                parameters = parent.parameters.merge(aliased.parameters),
            )
        }

        is SelectRawClause<*> -> {
            val parentDopeQuery = selectClause.parentClause?.toDopeQuery(this)
            val expressionDopeQuery = selectClause.expression.toDopeQuery(this)
            CouchbaseDopeQuery(
                queryString = formatQueryStringWithNullableFirst(parentDopeQuery, "SELECT RAW", expressionDopeQuery),
                parameters = parentDopeQuery?.parameters.orEmpty().merge(expressionDopeQuery.parameters),
            )
        }

        is SelectDistinctClause -> {
            val parentDopeQuery = selectClause.parentClause?.toDopeQuery(this)
            val expressionDopeQuery = selectClause.expression.toDopeQuery(this)
            val additionalExpressionDopeQueries = selectClause.expressions.map { it.toDopeQuery(this) }
            CouchbaseDopeQuery(
                queryString = formatQueryStringWithNullableFirst(
                    parentDopeQuery,
                    "SELECT DISTINCT",
                    expressionDopeQuery,
                    additionalExpressionDopeQueries,
                ),
                parameters = expressionDopeQuery.parameters.merge(
                    *additionalExpressionDopeQueries.map { it.parameters }
                        .toTypedArray(),
                ),
            )
        }

        is FromClause<*> -> {
            val parentDopeQuery = selectClause.parentClause.toDopeQuery(this)
            val fromableDopeQuery = when (val fromable = selectClause.fromable) {
                is AliasedBucket -> fromable.asBucketDefinition().toDopeQuery(this)
                is AliasedSelectClause<*> -> fromable.asAliasedSelectClauseDefinition().toDopeQuery(this)
                else -> selectClause.fromable.toDopeQuery(this)
            }
            CouchbaseDopeQuery(
                queryString = formatToQueryStringWithSymbol(
                    parentDopeQuery.queryString,
                    "FROM",
                    fromableDopeQuery.queryString,
                ),
                parameters = parentDopeQuery.parameters.merge(fromableDopeQuery.parameters),
            )
        }

        is GroupByClause<*> -> {
            val parent = selectClause.parentClause.toDopeQuery(this)
            val first = selectClause.field.toDopeQuery(this)
            val additionalFieldDopeQueries = selectClause.fields.map { it.toDopeQuery(this) }
            CouchbaseDopeQuery(
                queryString = formatToQueryStringWithSymbol(
                    parent.queryString,
                    "GROUP BY",
                    first.queryString,
                    *additionalFieldDopeQueries.map { it.queryString }.toTypedArray(),
                ),
                parameters = parent.parameters.merge(
                    first.parameters,
                    *additionalFieldDopeQueries.map { it.parameters }.toTypedArray(),
                ),
            )
        }

        is SelectOrderByClause<*> -> {
            val parent = selectClause.parentClause.toDopeQuery(this)
            val first = selectClause.orderExpression.toDopeQuery(this)
            val additionalOrderExpressionDopeQueries =
                selectClause.additionalOrderExpressions.map { it.toDopeQuery(this) }
            CouchbaseDopeQuery(
                queryString = formatToQueryStringWithSymbol(
                    parent.queryString,
                    "ORDER BY",
                    first.queryString,
                    *additionalOrderExpressionDopeQueries.map { it.queryString }.toTypedArray(),
                ),
                parameters = parent.parameters.merge(
                    first.parameters,
                    *additionalOrderExpressionDopeQueries.map { it.parameters }.toTypedArray(),
                ),
            )
        }

        is LetClause<*> -> {
            val parent = selectClause.parentClause.toDopeQuery(this)
            val first = selectClause.dopeVariable
            val firstDope = first.toLetDefinitionDopeQuery(this)
            val additionalVariableAssignments = selectClause.dopeVariables.map { variable ->
                variable.toLetDefinitionDopeQuery(this)
            }
            CouchbaseDopeQuery(
                queryString = formatToQueryStringWithSymbol(
                    parent.queryString,
                    "LET",
                    firstDope.queryString,
                    *additionalVariableAssignments.map { it.queryString }.toTypedArray(),
                ),
                parameters = parent.parameters.merge(
                    firstDope.parameters,
                    *additionalVariableAssignments.map { it.parameters }.toTypedArray(),
                ),
            )
        }

        is SetOperator<*> -> {
            val left = selectClause.leftSelect.toDopeQuery(this)
            val right = selectClause.rightSelect.toDopeQuery(this)
            val all = if (selectClause.duplicatesAllowed) " ALL" else ""
            CouchbaseDopeQuery(
                queryString = "(${left.queryString}) ${selectClause.setOperatorType} $all (${right.queryString})".replace(
                    "  ",
                    " ",
                ),
                parameters = left.parameters.merge(right.parameters),
            )
        }

        is WindowClause<*> -> {
            val parent = selectClause.parentClause.toDopeQuery(this)
            val first = selectClause.windowDeclaration.toDopeQuery(this)
            val additionalWindowDeclarationDopeQueries = selectClause.windowDeclarations.map { it.toDopeQuery(this) }
            CouchbaseDopeQuery(
                queryString = formatToQueryStringWithSymbol(
                    parent.queryString,
                    "WINDOW",
                    first.queryString,
                    *additionalWindowDeclarationDopeQueries.map { it.queryString }.toTypedArray(),
                ),
                parameters = parent.parameters.merge(
                    first.parameters,
                    *additionalWindowDeclarationDopeQueries.map { it.parameters }.toTypedArray(),
                ),
            )
        }

        is MergeableClause<*> -> resolve(selectClause)

        else -> throw UnsupportedOperationException("Unsupported selectClause type: ${selectClause.javaClass.name}")
    }

    private fun <T : ValidType> DopeVariable<T>.toLetDefinitionDopeQuery(resolver: QueryResolver<CouchbaseDopeQuery>): CouchbaseDopeQuery {
        val valueDopeQuery = value.toDopeQuery(resolver)
        return CouchbaseDopeQuery(
            queryString = "`$name` = ${valueDopeQuery.queryString}",
            parameters = valueDopeQuery.parameters,
        )
    }
}
