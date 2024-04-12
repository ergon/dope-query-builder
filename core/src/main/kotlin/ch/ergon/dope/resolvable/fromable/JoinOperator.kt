package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.resolvable.clause.select.Fromable
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

sealed class JoinOperator : Fromable {
    private val queryString: String

    constructor(fromBucket: Bucket, joinType: String, goalBucket: Bucket, onCondition: TypeExpression<BooleanType>) {
        queryString = "${fromBucket.toQueryString()} $joinType ${goalBucket.toQueryString()} ON ${onCondition.toQueryString()}"
    }

    constructor(fromBucket: Bucket, joinType: String, goalBucket: Bucket, key: Field<out ValidType>) {
        queryString = "${fromBucket.toQueryString()} $joinType ${goalBucket.toQueryString()} ON KEYS ${key.toQueryString()}"
    }

    override fun toQueryString(): String = queryString
}

class StandardJoinOperator : JoinOperator {
    constructor(fromBucket: Bucket, goalBucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(fromBucket, "JOIN", goalBucket, onCondition)

    constructor(fromBucket: Bucket, goalBucket: Bucket, onKeys: Field<out ValidType>) :
        super(fromBucket, "JOIN", goalBucket, onKeys)
}

class LeftJoinOperator : JoinOperator {
    constructor(fromBucket: Bucket, goalBucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(fromBucket, "LEFT JOIN", goalBucket, onCondition)

    constructor(fromBucket: Bucket, goalBucket: Bucket, onKeys: Field<out ValidType>) :
        super(fromBucket, "LEFT JOIN", goalBucket, onKeys)
}

class InnerJoinOperator : JoinOperator {
    constructor(fromBucket: Bucket, goalBucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(fromBucket, "INNER JOIN", goalBucket, onCondition)

    constructor(fromBucket: Bucket, goalBucket: Bucket, onKeys: Field<out ValidType>) :
        super(fromBucket, "INNER JOIN", goalBucket, onKeys)
}

class RightJoinOperator : JoinOperator {
    constructor(fromBucket: Bucket, goalBucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(fromBucket, "RIGHT JOIN", goalBucket, onCondition)

    constructor(fromBucket: Bucket, goalBucket: Bucket, onKeys: Field<out ValidType>) :
        super(fromBucket, "RIGHT JOIN", goalBucket, onKeys)
}
