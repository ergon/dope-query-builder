package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectJoinClause
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.clause.model.JoinType.INNER_JOIN
import ch.ergon.dope.resolvable.clause.model.JoinType.JOIN
import ch.ergon.dope.resolvable.clause.model.JoinType.LEFT_JOIN
import ch.ergon.dope.resolvable.clause.model.JoinType.RIGHT_JOIN
import ch.ergon.dope.resolvable.clause.model.OnType.ON
import ch.ergon.dope.resolvable.clause.model.OnType.ON_KEYS
import ch.ergon.dope.resolvable.clause.model.OnType.ON_KEY_FOR
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

private enum class JoinType(val type: String) {
    JOIN("JOIN"),
    LEFT_JOIN("LEFT JOIN"),
    INNER_JOIN("INNER JOIN"),
    RIGHT_JOIN("RIGHT JOIN"),
}

private enum class OnType {
    ON,
    ON_KEYS,
    ON_KEY_FOR,
}

sealed class SelectJoinClause<T : ValidType> : ISelectJoinClause<T> {
    private val joinType: JoinType
    private val joinable: Joinable
    private val onCondition: TypeExpression<BooleanType>?
    private val onKeys: TypeExpression<out ValidType>?
    private val onKey: TypeExpression<out ValidType>?
    private val forBucket: Bucket?
    private val hashOrNestedLoopHint: HashOrNestedLoopHint?
    private val keysOrIndexHint: KeysOrIndexHint?
    private val parentClause: ISelectFromClause<T>
    private val onType: OnType

    constructor(
        joinType: JoinType,
        joinable: Joinable,
        onCondition: TypeExpression<BooleanType>? = null,
        onKeys: TypeExpression<out ValidType>? = null,
        onKey: TypeExpression<out ValidType>? = null,
        forBucket: Bucket? = null,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
        parentClause: ISelectFromClause<T>,
    ) {
        this.onType = when {
            onCondition != null -> ON
            onKeys != null -> ON_KEYS
            onKey != null -> ON_KEY_FOR
            else -> throw IllegalArgumentException("One of onCondition, onKeys or onKey must be provided for JoinClause.")
        }
        this.joinType = joinType
        this.joinable = joinable
        this.onCondition = onCondition
        this.onKeys = onKeys
        this.onKey = onKey
        this.forBucket = forBucket
        this.parentClause = parentClause
        this.hashOrNestedLoopHint = hashOrNestedLoopHint
        this.keysOrIndexHint = keysOrIndexHint
    }

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val joinableDopeQuery = when (joinable) {
            is AliasedBucket -> joinable.asBucketDefinition().toDopeQuery(manager)
            is AliasedSelectClause<*> -> joinable.asAliasedSelectClauseDefinition().toDopeQuery(manager)
            else -> joinable.toDopeQuery(manager)
        }
        val joinHintsDopeQuery = if (hashOrNestedLoopHint != null || keysOrIndexHint != null) {
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
        val joinQueryString = "${parentDopeQuery.queryString} ${joinType.type} ${joinableDopeQuery.queryString}" +
            joinHintsDopeQuery?.let { " ${joinHintsDopeQuery.queryString}" }.orEmpty()
        val joinParameters = parentDopeQuery.parameters.merge(
            joinableDopeQuery.parameters,
            joinHintsDopeQuery?.parameters,
        )

        return when (onType) {
            ON -> {
                val onConditionDopeQuery = onCondition?.toDopeQuery(manager)
                DopeQuery(
                    queryString = "$joinQueryString ON ${onConditionDopeQuery?.queryString}",
                    parameters = joinParameters.merge(onConditionDopeQuery?.parameters),
                )
            }

            ON_KEYS -> {
                val keyDopeQuery = onKeys?.toDopeQuery(manager)
                DopeQuery(
                    queryString = "$joinQueryString ON KEYS ${keyDopeQuery?.queryString}",
                    parameters = joinParameters.merge(keyDopeQuery?.parameters),
                )
            }

            ON_KEY_FOR -> {
                val keyDopeQuery = onKey?.toDopeQuery(manager)
                val forBucketDopeQuery = forBucket?.toDopeQuery(manager)
                DopeQuery(
                    queryString = "$joinQueryString ON KEY ${keyDopeQuery?.queryString} " +
                        "FOR ${forBucketDopeQuery?.queryString}",
                    parameters = joinParameters.merge(keyDopeQuery?.parameters, forBucketDopeQuery?.parameters),
                )
            }
        }
    }
}

class StandardJoinOnConditionClause<T : ValidType>(
    joinable: Joinable,
    onCondition: TypeExpression<BooleanType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = JOIN,
    joinable = joinable,
    onCondition = onCondition,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class StandardJoinOnKeysClause<T : ValidType>(
    joinable: Joinable,
    onKeys: TypeExpression<out ValidType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = JOIN,
    joinable = joinable,
    onKeys = onKeys,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class StandardJoinOnKeyClause<T : ValidType>(
    joinable: Joinable,
    onKey: TypeExpression<out ValidType>,
    forBucket: Bucket,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = JOIN,
    joinable = joinable,
    onKey = onKey,
    forBucket = forBucket,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class LeftJoinOnConditionClause<T : ValidType>(
    joinable: Joinable,
    onCondition: TypeExpression<BooleanType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = LEFT_JOIN,
    joinable = joinable,
    onCondition = onCondition,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class LeftJoinOnKeysClause<T : ValidType>(
    joinable: Joinable,
    onKeys: TypeExpression<out ValidType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = LEFT_JOIN,
    joinable = joinable,
    onKeys = onKeys,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class LeftJoinOnKeyClause<T : ValidType>(
    joinable: Joinable,
    onKey: TypeExpression<out ValidType>,
    forBucket: Bucket,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = LEFT_JOIN,
    joinable = joinable,
    onKey = onKey,
    forBucket = forBucket,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class InnerJoinOnConditionClause<T : ValidType>(
    joinable: Joinable,
    onCondition: TypeExpression<BooleanType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = INNER_JOIN,
    joinable = joinable,
    onCondition = onCondition,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class InnerJoinOnKeysClause<T : ValidType>(
    joinable: Joinable,
    onKeys: TypeExpression<out ValidType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = INNER_JOIN,
    joinable = joinable,
    onKeys = onKeys,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class InnerJoinOnKeyClause<T : ValidType>(
    joinable: Joinable,
    onKey: TypeExpression<out ValidType>,
    forBucket: Bucket,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = INNER_JOIN,
    joinable = joinable,
    onKey = onKey,
    forBucket = forBucket,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class RightJoinClause<T : ValidType>(
    joinable: Joinable,
    onCondition: TypeExpression<BooleanType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = RIGHT_JOIN,
    joinable = joinable,
    onCondition = onCondition,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)
