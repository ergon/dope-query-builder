package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectJoinClause
import ch.ergon.dope.resolvable.clause.model.JoinType.INNER_JOIN
import ch.ergon.dope.resolvable.clause.model.JoinType.JOIN
import ch.ergon.dope.resolvable.clause.model.JoinType.LEFT_JOIN
import ch.ergon.dope.resolvable.clause.model.JoinType.RIGHT_JOIN
import ch.ergon.dope.resolvable.clause.model.OnType.ON
import ch.ergon.dope.resolvable.clause.model.OnType.ON_KEYS
import ch.ergon.dope.resolvable.clause.model.OnType.ON_KEY_FOR
import ch.ergon.dope.resolvable.clause.model.joinHint.UseHashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.model.joinHint.UseKeysOrIndexHint
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.Joinable
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

sealed class SelectJoinClause : ISelectJoinClause {
    private val joinType: JoinType
    private val joinable: Joinable
    private val onCondition: TypeExpression<BooleanType>?
    private val onKeys: Field<out ValidType>?
    private val onKey: Field<out ValidType>?
    private val forBucket: Bucket?
    private val hashOrNestedLoopHint: UseHashOrNestedLoopHint?
    private val keysOrIndexHint: UseKeysOrIndexHint?
    private val parentClause: ISelectFromClause
    private val onType: OnType

    constructor(
        joinType: JoinType,
        joinable: Joinable,
        onCondition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: UseHashOrNestedLoopHint? = null,
        keysOrIndexHint: UseKeysOrIndexHint? = null,
        parentClause: ISelectFromClause,
    ) {
        this.onType = ON
        this.joinType = joinType
        this.joinable = joinable
        this.onCondition = onCondition
        this.parentClause = parentClause
        this.onKeys = null
        this.onKey = null
        this.forBucket = null
        this.hashOrNestedLoopHint = hashOrNestedLoopHint
        this.keysOrIndexHint = keysOrIndexHint
    }

    constructor(
        joinType: JoinType,
        joinable: Joinable,
        onKeys: Field<out ValidType>,
        hashOrNestedLoopHint: UseHashOrNestedLoopHint? = null,
        keysOrIndexHint: UseKeysOrIndexHint? = null,
        parentClause: ISelectFromClause,
    ) {
        this.onType = ON_KEYS
        this.joinType = joinType
        this.joinable = joinable
        this.onKeys = onKeys
        this.parentClause = parentClause
        this.onCondition = null
        this.onKey = null
        this.forBucket = null
        this.hashOrNestedLoopHint = hashOrNestedLoopHint
        this.keysOrIndexHint = keysOrIndexHint
    }

    constructor(
        joinType: JoinType,
        joinable: Joinable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
        hashOrNestedLoopHint: UseHashOrNestedLoopHint? = null,
        keysOrIndexHint: UseKeysOrIndexHint? = null,
        parentClause: ISelectFromClause,
    ) {
        this.onType = ON_KEY_FOR
        this.joinType = joinType
        this.joinable = joinable
        this.onKey = onKey
        this.forBucket = forBucket
        this.parentClause = parentClause
        this.onCondition = null
        this.onKeys = null
        this.hashOrNestedLoopHint = hashOrNestedLoopHint
        this.keysOrIndexHint = keysOrIndexHint
    }

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val joinableDopeQuery = when (joinable) {
            is AliasedBucket -> joinable.asBucketDefinition().toDopeQuery(manager)
            else -> joinable.toDopeQuery(manager)
        }
        val joinHints = if (hashOrNestedLoopHint != null || keysOrIndexHint != null) {
            "USE" +
                hashOrNestedLoopHint?.let { " ${hashOrNestedLoopHint.queryString}" }.orEmpty() +
                keysOrIndexHint?.let { " ${keysOrIndexHint.toDopeQuery(manager).queryString}" }.orEmpty()
        } else {
            null
        }
        val joinQueryString = "${parentDopeQuery.queryString} ${joinType.type} ${joinableDopeQuery.queryString}" +
            joinHints?.let { " $joinHints" }.orEmpty()
        val joinParameters = parentDopeQuery.parameters + joinableDopeQuery.parameters

        return when (onType) {
            ON -> {
                val onConditionDopeQuery = onCondition?.toDopeQuery(manager)
                DopeQuery(
                    queryString = "$joinQueryString ON ${onConditionDopeQuery?.queryString}",
                    parameters = joinParameters + onConditionDopeQuery?.parameters.orEmpty(),
                )
            }

            ON_KEYS -> {
                val keyDopeQuery = onKeys?.toDopeQuery(manager)
                DopeQuery(
                    queryString = "$joinQueryString ON KEYS ${keyDopeQuery?.queryString}",
                    parameters = joinParameters + keyDopeQuery?.parameters.orEmpty(),
                )
            }

            ON_KEY_FOR -> {
                val keyDopeQuery = onKey?.toDopeQuery(manager)
                val forBucketDopeQuery = forBucket?.toDopeQuery(manager)
                DopeQuery(
                    queryString = "$joinQueryString ON KEY ${keyDopeQuery?.queryString} " +
                        "FOR ${forBucketDopeQuery?.queryString}",
                    parameters = joinParameters + keyDopeQuery?.parameters.orEmpty() +
                        forBucketDopeQuery?.parameters.orEmpty(),
                )
            }
        }
    }
}

class StandardJoinClause : SelectJoinClause {
    constructor(
        joinable: Joinable,
        onCondition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: UseHashOrNestedLoopHint? = null,
        keysOrIndexHint: UseKeysOrIndexHint? = null,
        parentClause: ISelectFromClause,
    ) : super(JOIN, joinable, onCondition, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)

    constructor(
        joinable: Joinable,
        onKeys: Field<out ValidType>,
        hashOrNestedLoopHint: UseHashOrNestedLoopHint? = null,
        keysOrIndexHint: UseKeysOrIndexHint? = null,
        parentClause: ISelectFromClause,
    ) : super(JOIN, joinable, onKeys, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)

    constructor(
        joinable: Joinable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
        hashOrNestedLoopHint: UseHashOrNestedLoopHint? = null,
        keysOrIndexHint: UseKeysOrIndexHint? = null,
        parentClause: ISelectFromClause,
    ) : super(JOIN, joinable, onKey, forBucket, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)
}

class LeftJoinClause : SelectJoinClause {
    constructor(
        joinable: Joinable,
        onCondition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: UseHashOrNestedLoopHint? = null,
        keysOrIndexHint: UseKeysOrIndexHint? = null,
        parentClause: ISelectFromClause,
    ) : super(LEFT_JOIN, joinable, onCondition, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)

    constructor(
        joinable: Joinable,
        onKeys: Field<out ValidType>,
        hashOrNestedLoopHint: UseHashOrNestedLoopHint? = null,
        useKeysOrIndexHint: UseKeysOrIndexHint? = null,
        parentClause: ISelectFromClause,
    ) : super(LEFT_JOIN, joinable, onKeys, hashOrNestedLoopHint, useKeysOrIndexHint, parentClause = parentClause)

    constructor(
        joinable: Joinable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
        hashOrNestedLoopHint: UseHashOrNestedLoopHint? = null,
        keysOrIndexHint: UseKeysOrIndexHint? = null,
        parentClause: ISelectFromClause,
    ) : super(LEFT_JOIN, joinable, onKey, forBucket, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)
}

class InnerJoinClause : SelectJoinClause {
    constructor(
        joinable: Joinable,
        onCondition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: UseHashOrNestedLoopHint? = null,
        keysOrIndexHint: UseKeysOrIndexHint? = null,
        parentClause: ISelectFromClause,
    ) : super(INNER_JOIN, joinable, onCondition, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)

    constructor(
        joinable: Joinable,
        onKeys: Field<out ValidType>,
        hashOrNestedLoopHint: UseHashOrNestedLoopHint? = null,
        keysOrIndexHint: UseKeysOrIndexHint? = null,
        parentClause: ISelectFromClause,
    ) : super(INNER_JOIN, joinable, onKeys, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)

    constructor(
        joinable: Joinable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
        hashOrNestedLoopHint: UseHashOrNestedLoopHint? = null,
        keysOrIndexHint: UseKeysOrIndexHint? = null,
        parentClause: ISelectFromClause,
    ) : super(INNER_JOIN, joinable, onKey, forBucket, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)
}

class RightJoinClause(
    joinable: Joinable,
    onCondition: TypeExpression<BooleanType>,
    hashOrNestedLoopHint: UseHashOrNestedLoopHint? = null,
    keysOrIndexHint: UseKeysOrIndexHint? = null,
    parentClause: ISelectFromClause,
) : SelectJoinClause(RIGHT_JOIN, joinable, onCondition, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)
