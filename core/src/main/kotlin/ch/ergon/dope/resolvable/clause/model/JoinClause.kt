package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectJoinClause
import ch.ergon.dope.resolvable.clause.model.JoinType.INNER_JOIN
import ch.ergon.dope.resolvable.clause.model.JoinType.JOIN
import ch.ergon.dope.resolvable.clause.model.JoinType.LEFT_JOIN
import ch.ergon.dope.resolvable.clause.model.JoinType.RIGHT_JOIN
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

private enum class JoinType(val type: String) {
    JOIN("JOIN"),
    LEFT_JOIN("LEFT JOIN"),
    INNER_JOIN("INNER JOIN"),
    RIGHT_JOIN("RIGHT JOIN"),
}

sealed class SelectJoinClause : ISelectJoinClause {
    private val joinType: JoinType
    private val bucket: Bucket
    private val onCondition: TypeExpression<BooleanType>?
    private val onKeys: Field<out ValidType>?
    private val onKey: Field<out ValidType>?
    private val forBucket: Bucket?
    private val parentClause: ISelectFromClause

    constructor(joinType: JoinType, bucket: Bucket, onCondition: TypeExpression<BooleanType>, parentClause: ISelectFromClause) {
        this.joinType = joinType
        this.bucket = bucket
        this.onCondition = onCondition
        this.parentClause = parentClause
        this.onKeys = null
        this.onKey = null
        this.forBucket = null
    }

    constructor(joinType: JoinType, bucket: Bucket, onKeys: Field<out ValidType>, parentClause: ISelectFromClause) {
        this.joinType = joinType
        this.bucket = bucket
        this.onKeys = onKeys
        this.parentClause = parentClause
        this.onCondition = null
        this.onKey = null
        this.forBucket = null
    }

    constructor(joinType: JoinType, bucket: Bucket, onKey: Field<out ValidType>, forBucket: Bucket, parentClause: ISelectFromClause) {
        this.joinType = joinType
        this.bucket = bucket
        this.onKey = onKey
        this.forBucket = forBucket
        this.parentClause = parentClause
        this.onCondition = null
        this.onKeys = null
    }

    override fun toDopeQuery(): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery()
        val bucketDopeQuery = bucket.toDopeQuery()

        return when {
            onCondition != null -> {
                val onConditionDopeQuery = onCondition.toDopeQuery()
                DopeQuery(
                    queryString = "${parentDopeQuery.queryString} ${joinType.type} ${bucketDopeQuery.queryString} " +
                        "ON ${onConditionDopeQuery.queryString}",
                    parameters = parentDopeQuery.parameters + bucketDopeQuery.parameters + onConditionDopeQuery.parameters,
                )
            }

            onKeys != null -> {
                val keyDopeQuery = onKeys.toDopeQuery()
                DopeQuery(
                    queryString = "${parentDopeQuery.queryString} ${joinType.type} ${bucketDopeQuery.queryString} " +
                        "ON KEYS ${keyDopeQuery.queryString}",
                    parameters = parentDopeQuery.parameters + bucketDopeQuery.parameters + keyDopeQuery.parameters,
                )
            }

            onKey != null && forBucket != null -> {
                val keyDopeQuery = onKey.toDopeQuery()
                val forBucketDopeQuery = forBucket.toDopeQuery()
                DopeQuery(
                    queryString = "${parentDopeQuery.queryString} ${joinType.type} ${bucketDopeQuery.queryString} " +
                        "ON KEY ${keyDopeQuery.queryString} FOR ${forBucketDopeQuery.queryString}",
                    parameters = parentDopeQuery.parameters + bucketDopeQuery.parameters + keyDopeQuery.parameters +
                        forBucketDopeQuery.parameters,
                )
            }

            else -> throw IllegalStateException("Unable to construct join clause")
        }
    }
}

class StandardJoinClause : SelectJoinClause {
    constructor(bucket: Bucket, onCondition: TypeExpression<BooleanType>, parentClause: ISelectFromClause) :
        super(JOIN, bucket, onCondition, parentClause)

    constructor(bucket: Bucket, onKeys: Field<out ValidType>, parentClause: ISelectFromClause) :
        super(JOIN, bucket, onKeys, parentClause)

    constructor(bucket: Bucket, onKey: Field<out ValidType>, forBucket: Bucket, parentClause: ISelectFromClause) :
        super(JOIN, bucket, onKey, forBucket, parentClause)
}

class LeftJoinClause : SelectJoinClause {
    constructor(bucket: Bucket, onCondition: TypeExpression<BooleanType>, parentClause: ISelectFromClause) :
        super(LEFT_JOIN, bucket, onCondition, parentClause)

    constructor(bucket: Bucket, onKeys: Field<out ValidType>, parentClause: ISelectFromClause) :
        super(LEFT_JOIN, bucket, onKeys, parentClause)

    constructor(bucket: Bucket, onKey: Field<out ValidType>, forBucket: Bucket, parentClause: ISelectFromClause) :
        super(LEFT_JOIN, bucket, onKey, forBucket, parentClause)
}

class InnerJoinClause : SelectJoinClause {
    constructor(bucket: Bucket, onCondition: TypeExpression<BooleanType>, parentClause: ISelectFromClause) :
        super(INNER_JOIN, bucket, onCondition, parentClause)

    constructor(bucket: Bucket, onKeys: Field<out ValidType>, parentClause: ISelectFromClause) :
        super(INNER_JOIN, bucket, onKeys, parentClause)

    constructor(bucket: Bucket, onKey: Field<out ValidType>, forBucket: Bucket, parentClause: ISelectFromClause) :
        super(INNER_JOIN, bucket, onKey, forBucket, parentClause)
}

class RightJoinClause(bucket: Bucket, onCondition: TypeExpression<BooleanType>, parentClause: ISelectFromClause) :
    SelectJoinClause(RIGHT_JOIN, bucket, onCondition, parentClause)
