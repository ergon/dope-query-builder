package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

sealed class JoinClause : IJoinClause {
    private val queryString: String

    constructor(parentClause: IFromClause, joinType: String, goalBucket: Bucket, onCondition: TypeExpression<BooleanType>) {
        queryString = "${parentClause.toQueryString()} $joinType ${goalBucket.toQueryString()} ON ${onCondition.toQueryString()}"
    }

    constructor(parentClause: IFromClause, joinType: String, goalBucket: Bucket, key: Field<out ValidType>) {
        queryString = "${parentClause.toQueryString()} $joinType ${goalBucket.toQueryString()} ON KEYS ${key.toQueryString()}"
    }

    override fun toQueryString(): String = queryString
}

class StandardJoinClause : JoinClause {
    constructor(parentClause: IFromClause, goalBucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(parentClause, "JOIN", goalBucket, onCondition)

    constructor(parentClause: IFromClause, goalBucket: Bucket, onKeys: Field<out ValidType>) :
        super(parentClause, "JOIN", goalBucket, onKeys)
}

class LeftJoinClause : JoinClause {
    constructor(parentClause: IFromClause, goalBucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(parentClause, "LEFT JOIN", goalBucket, onCondition)

    constructor(parentClause: IFromClause, goalBucket: Bucket, onKeys: Field<out ValidType>) :
        super(parentClause, "LEFT JOIN", goalBucket, onKeys)
}

class InnerJoinClause : JoinClause {
    constructor(parentClause: IFromClause, goalBucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(parentClause, "INNER JOIN", goalBucket, onCondition)

    constructor(parentClause: IFromClause, goalBucket: Bucket, onKeys: Field<out ValidType>) :
        super(parentClause, "INNER JOIN", goalBucket, onKeys)
}

class RightJoinClause : JoinClause {
    constructor(parentClause: IFromClause, goalBucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(parentClause, "RIGHT JOIN", goalBucket, onCondition)

    constructor(parentClause: IFromClause, goalBucket: Bucket, onKeys: Field<out ValidType>) :
        super(parentClause, "RIGHT JOIN", goalBucket, onKeys)
}
