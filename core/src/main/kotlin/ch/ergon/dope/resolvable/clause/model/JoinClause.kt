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
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
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
    private val condition: TypeExpression<BooleanType>?
    private val keys: TypeExpression<ArrayType<StringType>>?
    private val key: TypeExpression<StringType>?
    private val bucket: Bucket?
    private val hashOrNestedLoopHint: HashOrNestedLoopHint?
    private val keysOrIndexHint: KeysOrIndexHint?
    private val parentClause: ISelectFromClause<T>
    private val onType: OnType

    constructor(
        joinType: JoinType,
        joinable: Joinable,
        condition: TypeExpression<BooleanType>? = null,
        keys: TypeExpression<ArrayType<StringType>>? = null,
        key: TypeExpression<StringType>? = null,
        bucket: Bucket? = null,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
        parentClause: ISelectFromClause<T>,
    ) {
        this.onType = when {
            condition != null -> ON
            keys != null || (key != null && bucket == null) -> ON_KEYS
            key != null && bucket != null -> ON_KEY_FOR
            else -> throw IllegalArgumentException("One of condition, keys or key must be provided for JoinClause.")
        }
        this.joinType = joinType
        this.joinable = joinable
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
                val conditionDopeQuery = condition?.toDopeQuery(manager)
                DopeQuery(
                    queryString = "$joinQueryString ON ${conditionDopeQuery?.queryString}",
                    parameters = joinParameters.merge(conditionDopeQuery?.parameters),
                )
            }

            ON_KEYS -> {
                val keyDopeQuery = when {
                    keys != null -> keys.toDopeQuery(manager)
                    key != null -> key.toDopeQuery(manager)
                    else -> null
                }
                DopeQuery(
                    queryString = "$joinQueryString ON KEYS ${keyDopeQuery?.queryString}",
                    parameters = joinParameters.merge(keyDopeQuery?.parameters),
                )
            }

            ON_KEY_FOR -> {
                val keyDopeQuery = key?.toDopeQuery(manager)
                val bucketDopeQuery = bucket?.toDopeQuery(manager)
                DopeQuery(
                    queryString = "$joinQueryString ON KEY ${keyDopeQuery?.queryString} " +
                        "FOR ${bucketDopeQuery?.queryString}",
                    parameters = joinParameters.merge(keyDopeQuery?.parameters, bucketDopeQuery?.parameters),
                )
            }
        }
    }
}

class StandardJoinOnConditionClause<T : ValidType>(
    joinable: Joinable,
    condition: TypeExpression<BooleanType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = JOIN,
    joinable = joinable,
    condition = condition,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class StandardJoinOnKeysClause<T : ValidType>(
    joinable: Joinable,
    keys: TypeExpression<ArrayType<StringType>>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = JOIN,
    joinable = joinable,
    keys = keys,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class StandardJoinOnKeyClause<T : ValidType>(
    joinable: Joinable,
    key: TypeExpression<StringType>,
    bucket: Bucket? = null,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = JOIN,
    joinable = joinable,
    key = key,
    bucket = bucket,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class LeftJoinOnConditionClause<T : ValidType>(
    joinable: Joinable,
    condition: TypeExpression<BooleanType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = LEFT_JOIN,
    joinable = joinable,
    condition = condition,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class LeftJoinOnKeysClause<T : ValidType>(
    joinable: Joinable,
    keys: TypeExpression<ArrayType<StringType>>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = LEFT_JOIN,
    joinable = joinable,
    keys = keys,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class LeftJoinOnKeyClause<T : ValidType>(
    joinable: Joinable,
    key: TypeExpression<StringType>,
    bucket: Bucket? = null,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = LEFT_JOIN,
    joinable = joinable,
    key = key,
    bucket = bucket,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class InnerJoinOnConditionClause<T : ValidType>(
    joinable: Joinable,
    condition: TypeExpression<BooleanType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = INNER_JOIN,
    joinable = joinable,
    condition = condition,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class InnerJoinOnKeysClause<T : ValidType>(
    joinable: Joinable,
    keys: TypeExpression<ArrayType<StringType>>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = INNER_JOIN,
    joinable = joinable,
    keys = keys,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class InnerJoinOnKeyClause<T : ValidType>(
    joinable: Joinable,
    key: TypeExpression<StringType>,
    bucket: Bucket? = null,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = INNER_JOIN,
    joinable = joinable,
    key = key,
    bucket = bucket,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class RightJoinClause<T : ValidType>(
    joinable: Joinable,
    condition: TypeExpression<BooleanType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : SelectJoinClause<T>(
    joinType = RIGHT_JOIN,
    joinable = joinable,
    condition = condition,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)
