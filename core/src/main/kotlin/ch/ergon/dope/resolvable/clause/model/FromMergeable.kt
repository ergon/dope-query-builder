package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.clause.model.OnType.ON
import ch.ergon.dope.resolvable.clause.model.OnType.ON_KEYS
import ch.ergon.dope.resolvable.clause.model.OnType.ON_KEY_FOR
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

enum class OnType {
    ON,
    ON_KEYS,
    ON_KEY_FOR,
}

sealed interface MergeType {
    val type: String
}

sealed class FromMergeable<T : ValidType> : ISelectFromClause<T> {
    private val mergeType: MergeType
    private val mergeable: Resolvable
    private val condition: TypeExpression<BooleanType>?
    private val keys: Field<out ValidType>?
    private val forBucket: Bucket?
    private val hashOrNestedLoopHint: HashOrNestedLoopHint?
    private val keysOrIndexHint: KeysOrIndexHint?
    private val parentClause: ISelectFromClause<T>
    private val onType: OnType

    constructor(
        mergeType: MergeType,
        mergeable: Resolvable,
        condition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
        parentClause: ISelectFromClause<T>,
    ) {
        this.onType = ON
        this.mergeType = mergeType
        this.mergeable = mergeable
        this.condition = condition
        this.parentClause = parentClause
        this.keys = null
        this.forBucket = null
        this.hashOrNestedLoopHint = hashOrNestedLoopHint
        this.keysOrIndexHint = keysOrIndexHint
    }

    constructor(
        mergeType: MergeType,
        mergeable: Resolvable,
        keys: Field<out ValidType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
        parentClause: ISelectFromClause<T>,
    ) {
        this.onType = ON_KEYS
        this.mergeType = mergeType
        this.mergeable = mergeable
        this.keys = keys
        this.parentClause = parentClause
        this.condition = null
        this.forBucket = null
        this.hashOrNestedLoopHint = hashOrNestedLoopHint
        this.keysOrIndexHint = keysOrIndexHint
    }

    constructor(
        mergeType: MergeType,
        mergeable: Resolvable,
        key: Field<out ValidType>,
        forBucket: Bucket,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
        parentClause: ISelectFromClause<T>,
    ) {
        this.onType = ON_KEY_FOR
        this.mergeType = mergeType
        this.mergeable = mergeable
        this.keys = key
        this.forBucket = forBucket
        this.parentClause = parentClause
        this.condition = null
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
                queryString = "USE" +
                    hashOrNestedLoopHintDopeQuery?.let { " ${it.queryString}" }.orEmpty() +
                    keysOrIndexHintDopeQuery?.let { " ${it.queryString}" }.orEmpty(),
                parameters = if (hashOrNestedLoopHintDopeQuery != null && keysOrIndexHintDopeQuery != null) {
                    hashOrNestedLoopHintDopeQuery.parameters.merge(keysOrIndexHintDopeQuery.parameters)
                } else {
                    hashOrNestedLoopHintDopeQuery?.parameters ?: keysOrIndexHintDopeQuery?.parameters!!
                },
            )
        } else {
            null
        }
        val mergeQueryString = "${parentDopeQuery.queryString} ${mergeType.type} ${mergeableDopeQuery.queryString}" +
            hintsDopeQuery?.let { " ${hintsDopeQuery.queryString}" }.orEmpty()
        val mergeParameters = parentDopeQuery.parameters.merge(
            mergeableDopeQuery.parameters,
            hintsDopeQuery?.parameters,
        )

        return when (onType) {
            ON -> {
                val conditionDopeQuery = condition?.toDopeQuery(manager)
                DopeQuery(
                    queryString = "$mergeQueryString ON ${conditionDopeQuery?.queryString}",
                    parameters = mergeParameters.merge(conditionDopeQuery?.parameters),
                )
            }

            ON_KEYS -> {
                val keyDopeQuery = keys?.toDopeQuery(manager)
                DopeQuery(
                    queryString = "$mergeQueryString ON KEYS ${keyDopeQuery?.queryString}",
                    parameters = mergeParameters.merge(keyDopeQuery?.parameters),
                )
            }

            ON_KEY_FOR -> {
                val keyDopeQuery = keys?.toDopeQuery(manager)
                val forBucketDopeQuery = forBucket?.toDopeQuery(manager)
                DopeQuery(
                    queryString = "$mergeQueryString ON KEY ${keyDopeQuery?.queryString} " +
                        "FOR ${forBucketDopeQuery?.queryString}",
                    parameters = mergeParameters.merge(keyDopeQuery?.parameters, forBucketDopeQuery?.parameters),
                )
            }
        }
    }
}
