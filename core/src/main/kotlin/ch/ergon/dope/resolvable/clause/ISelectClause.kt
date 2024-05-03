package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.fromable.AliasedSelectClause
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.Fromable
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

interface IOffsetClause : Clause

interface ILimitClause : IOffsetClause {
    fun offset(numberExpression: TypeExpression<NumberType>): OffsetClause = OffsetClause(numberExpression, this)
}

interface IOrderByClause : ILimitClause {
    fun limit(numberExpression: TypeExpression<NumberType>): LimitClause = LimitClause(numberExpression, this)
    fun limit(number: Number): LimitClause = limit(number.toNumberType())
}

interface IGroupByClause : IOrderByClause {
    fun orderBy(stringField: Field<StringType>): OrderByClause = OrderByClause(stringField, this)
    fun orderBy(stringField: Field<StringType>, orderByType: OrderByType): OrderByTypeClause = OrderByTypeClause(stringField, orderByType, this)
}

interface IWhereClause : IGroupByClause {
    fun groupBy(field: Field<out ValidType>, vararg fields: Field<out ValidType>): GroupByClause =
        GroupByClause(field, *fields, parentClause = this)
}

interface IFromClause : IWhereClause {
    fun where(whereExpression: TypeExpression<BooleanType>) = WhereClause(whereExpression, this)
}

interface IJoinClause : IFromClause {
    fun join(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = StandardJoinClause(this, bucket, onCondition)
    fun join(bucket: Bucket, onKeys: Field<out ValidType>) = StandardJoinClause(this, bucket, onKeys)

    fun innerJoin(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = InnerJoinClause(this, bucket, onCondition)
    fun innerJoin(bucket: Bucket, onKeys: Field<out ValidType>) = InnerJoinClause(this, bucket, onKeys)

    fun leftJoin(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = LeftJoinClause(this, bucket, onCondition)
    fun leftJoin(bucket: Bucket, onKeys: Field<out ValidType>) = LeftJoinClause(this, bucket, onKeys)

    fun rightJoin(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = RightJoinClause(this, bucket, onCondition)
    fun rightJoin(bucket: Bucket, onKeys: Field<out ValidType>) = RightJoinClause(this, bucket, onKeys)
}

interface ISelectClause : IFromClause {
    fun from(fromable: Fromable) = FromClause(fromable, this)

    fun alias(alias: String) = AliasedSelectClause(alias, this)
}
