package ch.ergon.dope.resolvable.clause.model.mergeable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.orEmpty
import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.util.formatPartsToQueryStringWithSpace
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

enum class OnType {
    ON,
    ON_KEYS,
    ON_KEY_FOR,
}

sealed interface MergeType {
    val type: String
}

sealed class MergeableClause<T : ValidType> : ISelectFromClause<T> {
    private val mergeType: MergeType
    private val mergeable: Resolvable
    private val condition: TypeExpression<BooleanType>?
    private val keys: TypeExpression<ArrayType<StringType>>?
    private val key: TypeExpression<StringType>?
    private val bucket: Bucket?
    private val hashOrNestedLoopHint: HashOrNestedLoopHint?
    private val keysOrIndexHint: KeysOrIndexHint?
    private val onType: OnType
    private val parentClause: ISelectFromClause<T>

    constructor(
        mergeType: MergeType,
        mergeable: Resolvable,
        condition: TypeExpression<BooleanType>? = null,
        keys: TypeExpression<ArrayType<StringType>>? = null,
        key: TypeExpression<StringType>? = null,
        bucket: Bucket? = null,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
        parentClause: ISelectFromClause<T>,
    ) {
        this.onType = when {
            condition != null -> OnType.ON
            keys != null || (key != null && bucket == null) -> OnType.ON_KEYS
            key != null && bucket != null -> OnType.ON_KEY_FOR
            else -> throw IllegalArgumentException("One of condition, keys or key must be provided for JoinClause.")
        }
        this.mergeType = mergeType
        this.mergeable = mergeable
        this.condition = condition
        this.keys = keys
        this.key = key
        this.bucket = bucket
        this.parentClause = parentClause
        this.hashOrNestedLoopHint = hashOrNestedLoopHint
        this.keysOrIndexHint = keysOrIndexHint
    }

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val mergeableDopeQuery = when (mergeable) {
            is AliasedBucket -> mergeable.asBucketDefinition().toDopeQuery(manager)
            is AliasedSelectClause<*> -> mergeable.asAliasedSelectClauseDefinition().toDopeQuery(manager)
            else -> mergeable.toDopeQuery(manager)
        }
        val hintsDopeQuery = if (hashOrNestedLoopHint != null || keysOrIndexHint != null) {
            val hashOrNestedLoopHintDopeQuery = hashOrNestedLoopHint?.toDopeQuery(manager)
            val keysOrIndexHintDopeQuery = keysOrIndexHint?.toDopeQuery(manager)
            DopeQuery(
                queryString = formatPartsToQueryStringWithSpace(
                    "USE",
                    hashOrNestedLoopHintDopeQuery?.queryString,
                    keysOrIndexHintDopeQuery?.queryString,
                ),
                parameters = hashOrNestedLoopHintDopeQuery?.parameters.orEmpty().merge(keysOrIndexHintDopeQuery?.parameters),
            )
        } else {
            null
        }
        val mergeQueryString = formatPartsToQueryStringWithSpace(
            parentDopeQuery.queryString,
            mergeType.type,
            mergeableDopeQuery.queryString,
            hintsDopeQuery?.queryString,
        )
        val mergeParameters = parentDopeQuery.parameters.merge(
            mergeableDopeQuery.parameters,
            hintsDopeQuery?.parameters,
        )

        return when (onType) {
            OnType.ON -> {
                val conditionDopeQuery = condition?.toDopeQuery(manager)
                DopeQuery(
                    queryString = "$mergeQueryString ON ${conditionDopeQuery?.queryString}",
                    parameters = mergeParameters.merge(conditionDopeQuery?.parameters),
                )
            }

            OnType.ON_KEYS -> {
                val keyDopeQuery = when {
                    keys != null -> keys.toDopeQuery(manager)
                    key != null -> key.toDopeQuery(manager)
                    else -> null
                }
                DopeQuery(
                    queryString = formatPartsToQueryStringWithSpace(mergeQueryString, "ON KEYS", keyDopeQuery?.queryString),
                    parameters = mergeParameters.merge(keyDopeQuery?.parameters),
                )
            }

            OnType.ON_KEY_FOR -> {
                val keyDopeQuery = key?.toDopeQuery(manager)
                val bucketDopeQuery = bucket?.toDopeQuery(manager)
                DopeQuery(
                    queryString = formatPartsToQueryStringWithSpace(
                        mergeQueryString,
                        "ON KEY",
                        keyDopeQuery?.queryString,
                        "FOR",
                        bucketDopeQuery?.queryString,
                    ),
                    parameters = mergeParameters.merge(keyDopeQuery?.parameters, bucketDopeQuery?.parameters),
                )
            }
        }
    }
}
