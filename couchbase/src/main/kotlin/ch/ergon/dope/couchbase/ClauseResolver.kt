package ch.ergon.dope.couchbase

import ch.ergon.dope.couchbase.util.formatPartsToQueryStringWithSpace
import ch.ergon.dope.couchbase.util.formatQueryStringWithNullableFirst
import ch.ergon.dope.couchbase.util.formatToQueryString
import ch.ergon.dope.couchbase.util.formatToQueryStringWithSymbol
import ch.ergon.dope.orEmpty
import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.SetOperator
import ch.ergon.dope.resolvable.clause.model.AliasedUnnestClause
import ch.ergon.dope.resolvable.clause.model.DeleteClause
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.GroupByClause
import ch.ergon.dope.resolvable.clause.model.LetClause
import ch.ergon.dope.resolvable.clause.model.LimitClause
import ch.ergon.dope.resolvable.clause.model.OffsetClause
import ch.ergon.dope.resolvable.clause.model.ReturningClause
import ch.ergon.dope.resolvable.clause.model.ReturningSingleClause
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.clause.model.SetClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.clause.model.UnsetClause
import ch.ergon.dope.resolvable.clause.model.UpdateClause
import ch.ergon.dope.resolvable.clause.model.WhereClause
import ch.ergon.dope.resolvable.clause.model.WindowClause
import ch.ergon.dope.resolvable.clause.model.mergeable.JoinType
import ch.ergon.dope.resolvable.clause.model.mergeable.MergeableClause
import ch.ergon.dope.resolvable.clause.model.mergeable.NestType
import ch.ergon.dope.resolvable.clause.model.mergeable.OnType

internal object ClauseResolver {
    fun resolve(resolver: CouchbaseResolver, clause: Clause) =
        when (clause) {
            is SelectClause -> {
                val parentDopeQuery = clause.parentClause?.toDopeQuery(resolver)
                val expressionDopeQuery = clause.expression.toDopeQuery(resolver)
                val expressionsDopeQuery = clause.expressions.map { it.toDopeQuery(resolver) }
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
                val parentDopeQuery = clause.parentClause.toDopeQuery(resolver)
                val arrayDopeQuery = clause.arrayTypeField.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, "UNNEST", arrayDopeQuery.queryString),
                    parameters = parentDopeQuery.parameters.merge(arrayDopeQuery.parameters),
                )
            }

            is AliasedUnnestClause<*, *> -> {
                val parent = clause.parentClause.toDopeQuery(resolver)
                val aliased = clause.aliasedTypeExpression.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(parent.queryString, "UNNEST", aliased.queryString),
                    parameters = parent.parameters.merge(aliased.parameters),
                )
            }

            is SelectRawClause<*> -> {
                val parentDopeQuery = clause.parentClause?.toDopeQuery(resolver)
                val expressionDopeQuery = clause.expression.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = formatQueryStringWithNullableFirst(parentDopeQuery, "SELECT RAW", expressionDopeQuery),
                    parameters = parentDopeQuery?.parameters.orEmpty().merge(expressionDopeQuery.parameters),
                )
            }

            is SelectDistinctClause -> {
                val parentDopeQuery = clause.parentClause?.toDopeQuery(resolver)
                val expressionDopeQuery = clause.expression.toDopeQuery(resolver)
                val additionalExpressionDopeQueries = clause.expressions.map { it.toDopeQuery(resolver) }
                CouchbaseDopeQuery(
                    queryString = formatQueryStringWithNullableFirst(
                        parentDopeQuery,
                        "SELECT DISTINCT",
                        expressionDopeQuery,
                        additionalExpressionDopeQueries,
                    ),
                    parameters = expressionDopeQuery.parameters.merge(*additionalExpressionDopeQueries.map { it.parameters }.toTypedArray()),
                )
            }

            is FromClause<*> -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(resolver)
                val fromableDopeQuery = when (val fromable = clause.fromable) {
                    is AliasedBucket -> fromable.asBucketDefinition().toDopeQuery(resolver)
                    is AliasedSelectClause<*> -> fromable.asAliasedSelectClauseDefinition().toDopeQuery(resolver)
                    else -> clause.fromable.toDopeQuery(resolver)
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

            is WhereClause -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(resolver)
                val whereDopeQuery = clause.whereExpression.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parentDopeQuery.queryString,
                        "WHERE",
                        whereDopeQuery.queryString,
                    ),
                    parameters = parentDopeQuery.parameters.merge(whereDopeQuery.parameters),
                )
            }

            is GroupByClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(resolver)
                val first = clause.field.toDopeQuery(resolver)
                val additionalFieldDopeQueries = clause.fields.map { it.toDopeQuery(resolver) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parent.queryString,
                        "GROUP BY",
                        first.queryString,
                        *additionalFieldDopeQueries.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = parent.parameters.merge(first.parameters, *additionalFieldDopeQueries.map { it.parameters }.toTypedArray()),
                )
            }

            is SelectOrderByClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(resolver)
                val first = clause.orderExpression.toDopeQuery(resolver)
                val additionalOrderExpressionDopeQueries = clause.additionalOrderExpressions.map { it.toDopeQuery(resolver) }
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
                val parent = clause.parentClause.toDopeQuery(resolver)
                val first = clause.dopeVariable
                val firstDope = first.toLetDefinitionDopeQuery(resolver)
                val additionalVariableAssignments = clause.dopeVariables.map { variable ->
                    variable.toLetDefinitionDopeQuery(resolver)
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

            is ReturningClause -> {
                val parent = clause.parentClause.toDopeQuery(resolver)
                val returnables = arrayOf(clause.returnable) + clause.additionalReturnables
                val returnableDope = returnables.map {
                    when (it) {
                        is AliasedSelectClause<*> -> it.asAliasedSelectClauseDefinition().toDopeQuery(resolver)
                        else -> it.toDopeQuery(resolver)
                    }
                }
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parent.queryString,
                        "RETURNING",
                        *returnableDope.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = parent.parameters.merge(*returnableDope.map { it.parameters }.toTypedArray()),
                )
            }

            is ReturningSingleClause -> {
                val parent = clause.parentClause.toDopeQuery(resolver)
                val single = clause.singleReturnable.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = "${parent.queryString} RETURNING ${clause.returningType.name} ${single.queryString}",
                    parameters = parent.parameters.merge(single.parameters),
                )
            }

            is UpdateClause -> {
                val updatable = when (val u = clause.updatable) {
                    is AliasedBucket -> u.asBucketDefinition().toDopeQuery(resolver)
                    else -> clause.updatable.toDopeQuery(resolver)
                }
                CouchbaseDopeQuery(
                    queryString = "UPDATE ${updatable.queryString}",
                    parameters = updatable.parameters,
                )
            }

            is DeleteClause -> {
                val bucket = when (val d = clause.deletable) {
                    is AliasedBucket -> d.asBucketDefinition().toDopeQuery(resolver)
                    else -> clause.deletable.toDopeQuery(resolver)
                }
                CouchbaseDopeQuery(
                    queryString = "DELETE FROM ${bucket.queryString}",
                    parameters = bucket.parameters,
                )
            }

            is SetClause -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(resolver)
                val firstAssignmentDopeQuery = clause.setAssignment.toDopeQuery(resolver)
                val additionalAssignmentDopeQueries = clause.setAssignments.map { it.toDopeQuery(resolver) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryString(
                        "${parentDopeQuery.queryString} SET",
                        firstAssignmentDopeQuery.queryString,
                        *additionalAssignmentDopeQueries.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = parentDopeQuery.parameters.merge(
                        firstAssignmentDopeQuery.parameters,
                        *additionalAssignmentDopeQueries.map { it.parameters }.toTypedArray(),
                    ),
                )
            }

            is UnsetClause -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(resolver)
                val firstFieldDopeQuery = clause.field.toDopeQuery(resolver)
                val additionalFieldDopeQueries = clause.fields.map { it.toDopeQuery(resolver) }
                CouchbaseDopeQuery(
                    queryString = formatToQueryString(
                        "${parentDopeQuery.queryString} UNSET",
                        firstFieldDopeQuery.queryString,
                        *additionalFieldDopeQueries.map { it.queryString }.toTypedArray(),
                    ),
                    parameters = parentDopeQuery.parameters.merge(
                        firstFieldDopeQuery.parameters,
                        *additionalFieldDopeQueries.map { it.parameters }.toTypedArray(),
                    ),
                )
            }

            is SetOperator<*> -> {
                val left = clause.leftSelect.toDopeQuery(resolver)
                val right = clause.rightSelect.toDopeQuery(resolver)
                val all = if (clause.duplicatesAllowed) " ALL" else ""
                CouchbaseDopeQuery(
                    queryString = "(${left.queryString}) ${clause.setOperatorType} $all (${right.queryString})".replace("  ", " "),
                    parameters = left.parameters.merge(right.parameters),
                )
            }

            is WindowClause<*> -> {
                val parent = clause.parentClause.toDopeQuery(resolver)
                val first = clause.windowDeclaration.toDopeQuery(resolver)
                val additionalWindowDeclarationDopeQueries = clause.windowDeclarations.map { it.toDopeQuery(resolver) }
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

            is MergeableClause<*> -> {
                val onType = when {
                    clause.condition != null -> OnType.ON
                    clause.keys != null || (clause.key != null && clause.bucket == null) -> OnType.ON_KEYS
                    clause.key != null && clause.bucket != null -> OnType.ON_KEY_FOR
                    else -> throw IllegalArgumentException("One of condition, keys or key must be provided for JoinClause.")
                }
                val parent = clause.parentClause.toDopeQuery(resolver)
                val mergeable = when (val m = clause.mergeable) {
                    is AliasedBucket -> m.asBucketDefinition().toDopeQuery(resolver)
                    is AliasedSelectClause<*> -> m.asAliasedSelectClauseDefinition().toDopeQuery(resolver)
                    else -> clause.mergeable.toDopeQuery(resolver)
                }
                val hint = if (clause.hashOrNestedLoopHint != null || clause.keysOrIndexHint != null) {
                    val hashOrNestedLoopHintDopeQuery = clause.hashOrNestedLoopHint?.toDopeQuery(resolver)
                    val k = clause.keysOrIndexHint?.toDopeQuery(resolver)
                    CouchbaseDopeQuery(
                        formatPartsToQueryStringWithSpace("USE", hashOrNestedLoopHintDopeQuery?.queryString, k?.queryString),
                        hashOrNestedLoopHintDopeQuery?.parameters.orEmpty().merge(k?.parameters),
                    )
                } else {
                    null
                }
                val mergeTypeToken = when (val type = clause.mergeType) {
                    is JoinType -> when (type) {
                        JoinType.JOIN -> "JOIN"
                        JoinType.LEFT_JOIN -> "LEFT JOIN"
                        JoinType.INNER_JOIN -> "INNER JOIN"
                        JoinType.RIGHT_JOIN -> "RIGHT JOIN"
                    }

                    is NestType -> when (type) {
                        NestType.NEST -> "NEST"
                        NestType.INNER_NEST -> "INNER NEST"
                        NestType.LEFT_NEST -> "LEFT NEST"
                    }
                }
                val baseQueryString =
                    formatPartsToQueryStringWithSpace(parent.queryString, mergeTypeToken, mergeable.queryString, hint?.queryString)
                val baseParams = parent.parameters.merge(mergeable.parameters, hint?.parameters)
                when (onType) {
                    OnType.ON -> {
                        val cond = clause.condition?.toDopeQuery(resolver)
                        CouchbaseDopeQuery("$baseQueryString ON ${cond?.queryString}", baseParams.merge(cond?.parameters))
                    }

                    OnType.ON_KEYS -> {
                        val keys = clause.keys
                        val clauseKey = clause.key
                        val key = when {
                            keys != null -> keys.toDopeQuery(resolver)

                            clauseKey != null -> clauseKey.toDopeQuery(resolver)

                            else -> null
                        }
                        CouchbaseDopeQuery(
                            formatPartsToQueryStringWithSpace(baseQueryString, "ON KEYS", key?.queryString),
                            baseParams.merge(key?.parameters),
                        )
                    }

                    OnType.ON_KEY_FOR -> {
                        val key = clause.key?.toDopeQuery(resolver)
                        val bucket = clause.bucket?.toDopeQuery(resolver)
                        CouchbaseDopeQuery(
                            formatPartsToQueryStringWithSpace(baseQueryString, "ON KEY", key?.queryString, "FOR", bucket?.queryString),
                            baseParams.merge(key?.parameters, bucket?.parameters),
                        )
                    }
                }
            }

            is LimitClause -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(resolver)
                val numberDopeQuery = clause.numberExpression.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parentDopeQuery.queryString,
                        "LIMIT",
                        numberDopeQuery.queryString,
                    ),
                    parameters = parentDopeQuery.parameters.merge(numberDopeQuery.parameters),
                )
            }

            is OffsetClause -> {
                val parentDopeQuery = clause.parentClause.toDopeQuery(resolver)
                val numberDopeQuery = clause.numberExpression.toDopeQuery(resolver)
                CouchbaseDopeQuery(
                    queryString = formatToQueryStringWithSymbol(
                        parentDopeQuery.queryString,
                        "OFFSET",
                        numberDopeQuery.queryString,
                    ),
                    parameters = parentDopeQuery.parameters.merge(numberDopeQuery.parameters),
                )
            }

            else -> throw UnsupportedOperationException("Not supported: $clause")
        }
}
