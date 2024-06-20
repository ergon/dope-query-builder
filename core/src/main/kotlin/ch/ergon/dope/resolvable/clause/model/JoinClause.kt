package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectJoinClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

private const val JOIN = "JOIN"
private const val LEFT_JOIN = "LEFT JOIN"
private const val INNER_JOIN = "INNER JOIN"

sealed class SelectJoinClause : ISelectJoinClause {
    private val dopeQuery: DopeQuery

    constructor(joinType: String, bucket: Bucket, onCondition: TypeExpression<BooleanType>, parentClause: ISelectFromClause) {
        val parentDopeQuery = parentClause.toDopeQuery()
        val bucketDopeQuery = bucket.toDopeQuery()
        val onConditionDopeQuery = onCondition.toDopeQuery()
        dopeQuery = DopeQuery(
            queryString = "${parentDopeQuery.queryString} $joinType ${bucketDopeQuery.queryString} ON ${onConditionDopeQuery.queryString}",
            parameters = parentDopeQuery.parameters + bucketDopeQuery.parameters + onConditionDopeQuery.parameters,
        )
    }

    constructor(joinType: String, bucket: Bucket, key: Field<out ValidType>, parentClause: ISelectFromClause) {
        val parentDopeQuery = parentClause.toDopeQuery()
        val bucketDopeQuery = bucket.toDopeQuery()
        val keyDopeQuery = key.toDopeQuery()
        dopeQuery = DopeQuery(
            queryString = "${parentDopeQuery.queryString} $joinType ${bucketDopeQuery.queryString} ON KEYS ${keyDopeQuery.queryString}",
            parameters = parentDopeQuery.parameters + bucketDopeQuery.parameters + keyDopeQuery.parameters,
        )
    }

    constructor(joinType: String, bucket: Bucket, key: Field<out ValidType>, forBucket: Bucket, parentClause: ISelectFromClause) {
        val parentDopeQuery = parentClause.toDopeQuery()
        val bucketDopeQuery = bucket.toDopeQuery()
        val keyDopeQuery = key.toDopeQuery()
        val forBucketDopeQuery = forBucket.toDopeQuery()
        dopeQuery = DopeQuery(
            queryString = "${parentDopeQuery.queryString} $joinType ${bucketDopeQuery.queryString} " +
                "ON KEY ${keyDopeQuery.queryString} FOR ${forBucketDopeQuery.queryString}",
            parameters = parentDopeQuery.parameters + bucketDopeQuery.parameters + keyDopeQuery.parameters,
        )
    }

    override fun toDopeQuery(): DopeQuery = dopeQuery
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
    SelectJoinClause("RIGHT JOIN", bucket, onCondition, parentClause)
