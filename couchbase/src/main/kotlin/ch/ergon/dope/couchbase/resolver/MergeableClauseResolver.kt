package ch.ergon.dope.couchbase.resolver

import ch.ergon.dope.couchbase.AbstractCouchbaseResolver
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.util.formatPartsToQueryStringWithSpace
import ch.ergon.dope.orEmpty
import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.clause.model.mergeable.JoinType
import ch.ergon.dope.resolvable.clause.model.mergeable.MergeableClause
import ch.ergon.dope.resolvable.clause.model.mergeable.NestType
import ch.ergon.dope.resolvable.clause.model.mergeable.OnType

interface MergeableClauseResolver : AbstractCouchbaseResolver {
    fun resolve(selectClause: MergeableClause<*>): CouchbaseDopeQuery {
        val onType = when {
            selectClause.condition != null -> OnType.ON
            selectClause.keys != null || (selectClause.key != null && selectClause.bucket == null) -> OnType.ON_KEYS
            selectClause.key != null && selectClause.bucket != null -> OnType.ON_KEY_FOR
            else -> throw IllegalArgumentException("One of condition, keys or key must be provided for JoinClause.")
        }
        val parent = selectClause.parentClause.toDopeQuery(this)
        val mergeable = when (val m = selectClause.mergeable) {
            is AliasedBucket -> m.asBucketDefinition().toDopeQuery(this)
            is AliasedSelectClause<*> -> m.asAliasedSelectClauseDefinition().toDopeQuery(this)
            else -> selectClause.mergeable.toDopeQuery(this)
        }
        val hint = if (selectClause.hashOrNestedLoopHint != null || selectClause.keysOrIndexHint != null) {
            val hashOrNestedLoopHintDopeQuery = selectClause.hashOrNestedLoopHint?.toDopeQuery(this)
            val k = selectClause.keysOrIndexHint?.toDopeQuery(this)
            CouchbaseDopeQuery(
                formatPartsToQueryStringWithSpace("USE", hashOrNestedLoopHintDopeQuery?.queryString, k?.queryString),
                hashOrNestedLoopHintDopeQuery?.parameters.orEmpty().merge(k?.parameters),
            )
        } else {
            null
        }
        val mergeTypeToken = when (val type = selectClause.mergeType) {
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
        return when (onType) {
            OnType.ON -> {
                val cond = selectClause.condition?.toDopeQuery(this)
                CouchbaseDopeQuery("$baseQueryString ON ${cond?.queryString}", baseParams.merge(cond?.parameters))
            }

            OnType.ON_KEYS -> {
                val keys = selectClause.keys
                val clauseKey = selectClause.key
                val key = when {
                    keys != null -> keys.toDopeQuery(this)

                    clauseKey != null -> clauseKey.toDopeQuery(this)

                    else -> null
                }
                CouchbaseDopeQuery(
                    formatPartsToQueryStringWithSpace(baseQueryString, "ON KEYS", key?.queryString),
                    baseParams.merge(key?.parameters),
                )
            }

            OnType.ON_KEY_FOR -> {
                val key = selectClause.key?.toDopeQuery(this)
                val bucket = selectClause.bucket?.toDopeQuery(this)
                CouchbaseDopeQuery(
                    formatPartsToQueryStringWithSpace(baseQueryString, "ON KEY", key?.queryString, "FOR", bucket?.queryString),
                    baseParams.merge(key?.parameters, bucket?.parameters),
                )
            }
        }
    }
}
