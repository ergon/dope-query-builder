package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectJoinClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

sealed class SelectJoinClause : ISelectJoinClause {
    private val dopeQuery: DopeQuery

    constructor(parentClause: ISelectFromClause, joinType: String, bucket: Bucket, onCondition: TypeExpression<BooleanType>) {
        val parentDopeQuery = parentClause.toQuery()
        val bucketDopeQuery = bucket.toQuery()
        val onConditionDopeQuery = onCondition.toQuery()
        dopeQuery = DopeQuery(
            queryString = "${parentDopeQuery.queryString} $joinType ${bucketDopeQuery.queryString} ON ${onConditionDopeQuery.queryString}",
            parameters = parentDopeQuery.parameters + bucketDopeQuery.parameters + onConditionDopeQuery.parameters,
        )
    }

    constructor(parentClause: ISelectFromClause, joinType: String, bucket: Bucket, key: Field<out ValidType>) {
        val parentDopeQuery = parentClause.toQuery()
        val bucketDopeQuery = bucket.toQuery()
        val keyDopeQuery = key.toQuery()
        dopeQuery = DopeQuery(
            queryString = "${parentDopeQuery.queryString} $joinType ${bucketDopeQuery.queryString} ON KEYS ${keyDopeQuery.queryString}",
            parameters = parentDopeQuery.parameters + bucketDopeQuery.parameters + keyDopeQuery.parameters,
        )
    }

    override fun toQuery(): DopeQuery = dopeQuery
}

class StandardJoinClause : SelectJoinClause {
    constructor(parentClause: ISelectFromClause, bucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(parentClause, "JOIN", bucket, onCondition)

    constructor(parentClause: ISelectFromClause, bucket: Bucket, onKeys: Field<out ValidType>) :
        super(parentClause, "JOIN", bucket, onKeys)
}

class LeftJoinClause : SelectJoinClause {
    constructor(parentClause: ISelectFromClause, bucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(parentClause, "LEFT JOIN", bucket, onCondition)

    constructor(parentClause: ISelectFromClause, bucket: Bucket, onKeys: Field<out ValidType>) :
        super(parentClause, "LEFT JOIN", bucket, onKeys)
}

class InnerJoinClause : SelectJoinClause {
    constructor(parentClause: ISelectFromClause, bucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(parentClause, "INNER JOIN", bucket, onCondition)

    constructor(parentClause: ISelectFromClause, bucket: Bucket, onKeys: Field<out ValidType>) :
        super(parentClause, "INNER JOIN", bucket, onKeys)
}

class RightJoinClause : SelectJoinClause {
    constructor(parentClause: ISelectFromClause, bucket: Bucket, onCondition: TypeExpression<BooleanType>) :
        super(parentClause, "RIGHT JOIN", bucket, onCondition)

    constructor(parentClause: ISelectFromClause, bucket: Bucket, onKeys: Field<out ValidType>) :
        super(parentClause, "RIGHT JOIN", bucket, onKeys)
}
