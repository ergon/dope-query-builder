package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

sealed class JoinOperator : IFromClause {
    private val queryString: String

    constructor(parent: IFromClause, joinType: String, goalBucket: Bucket, onCondition: TypeExpression<BooleanType>) {
        queryString = "${parent.toQueryString()} $joinType ${goalBucket.toQueryString()} ON ${onCondition.toQueryString()}"
    }

    constructor(parent: IFromClause, joinType: String, goalBucket: Bucket, key: Field<out ValidType>) {
        queryString = "${parent.toQueryString()} $joinType ${goalBucket.toQueryString()} ON KEYS ${key.toQueryString()}"
    }

    override fun toQueryString(): String = queryString
}

class StandardJoinClause : JoinOperator {
    constructor(parent: IFromClause, goalBucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(parent, "JOIN", goalBucket, onCondition)

    constructor(parent: IFromClause, goalBucket: Bucket, onKeys: Field<out ValidType>) :
        super(parent, "JOIN", goalBucket, onKeys)
}

class LeftJoinClause : JoinOperator {
    constructor(parent: IFromClause, goalBucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(parent, "LEFT JOIN", goalBucket, onCondition)

    constructor(parent: IFromClause, goalBucket: Bucket, onKeys: Field<out ValidType>) :
        super(parent, "LEFT JOIN", goalBucket, onKeys)
}

class InnerJoinClause : JoinOperator {
    constructor(parent: IFromClause, goalBucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(parent, "INNER JOIN", goalBucket, onCondition)

    constructor(parent: IFromClause, goalBucket: Bucket, onKeys: Field<out ValidType>) :
        super(parent, "INNER JOIN", goalBucket, onKeys)
}

class RightJoinClause : JoinOperator {
    constructor(parent: IFromClause, goalBucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(parent, "RIGHT JOIN", goalBucket, onCondition)

    constructor(parent: IFromClause, goalBucket: Bucket, onKeys: Field<out ValidType>) :
        super(parent, "RIGHT JOIN", goalBucket, onKeys)
}
