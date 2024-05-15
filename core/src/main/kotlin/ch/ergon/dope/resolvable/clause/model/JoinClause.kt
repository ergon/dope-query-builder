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

    constructor(joinType: String, bucket: Bucket, onCondition: TypeExpression<BooleanType>, parentClause: ISelectFromClause) {
        val parentDopeQuery = parentClause.toQuery()
        val bucketDopeQuery = bucket.toQuery()
        val onConditionDopeQuery = onCondition.toQuery()
        dopeQuery = DopeQuery(
            queryString = "${parentDopeQuery.queryString} $joinType ${bucketDopeQuery.queryString} ON ${onConditionDopeQuery.queryString}",
            parameters = parentDopeQuery.parameters + bucketDopeQuery.parameters + onConditionDopeQuery.parameters,
        )
    }

    constructor(joinType: String, bucket: Bucket, key: Field<out ValidType>, parentClause: ISelectFromClause) {
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
    constructor(bucket: Bucket, onCondition: TypeExpression<BooleanType>, parentClause: ISelectFromClause) :
        super("JOIN", bucket, onCondition, parentClause)

    constructor(bucket: Bucket, onKeys: Field<out ValidType>, parentClause: ISelectFromClause) :
        super("JOIN", bucket, onKeys, parentClause)
}

class LeftJoinClause : SelectJoinClause {
    constructor(bucket: Bucket, onCondition: TypeExpression<BooleanType>, parentClause: ISelectFromClause) :
        super("LEFT JOIN", bucket, onCondition, parentClause)

    constructor(bucket: Bucket, onKeys: Field<out ValidType>, parentClause: ISelectFromClause) :
        super("LEFT JOIN", bucket, onKeys, parentClause)
}

class InnerJoinClause : SelectJoinClause {
    constructor(bucket: Bucket, onCondition: TypeExpression<BooleanType>, parentClause: ISelectFromClause) :
        super("INNER JOIN", bucket, onCondition, parentClause)

    constructor(bucket: Bucket, onKeys: Field<out ValidType>, parentClause: ISelectFromClause) :
        super("INNER JOIN", bucket, onKeys, parentClause)
}

class RightJoinClause : SelectJoinClause {
    constructor(bucket: Bucket, onCondition: TypeExpression<BooleanType>, parentClause: ISelectFromClause) :
        super("RIGHT JOIN", bucket, onCondition, parentClause)

    constructor(bucket: Bucket, onKeys: Field<out ValidType>, parentClause: ISelectFromClause) :
        super("RIGHT JOIN", bucket, onKeys, parentClause)
}
