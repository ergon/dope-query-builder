package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.GroupByClause
import ch.ergon.dope.resolvable.clause.model.InnerJoinClause
import ch.ergon.dope.resolvable.clause.model.LeftJoinClause
import ch.ergon.dope.resolvable.clause.model.OrderByType
import ch.ergon.dope.resolvable.clause.model.RightJoinClause
import ch.ergon.dope.resolvable.clause.model.SelectLimitClause
import ch.ergon.dope.resolvable.clause.model.SelectOffsetClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByTypeClause
import ch.ergon.dope.resolvable.clause.model.SelectWhereClause
import ch.ergon.dope.resolvable.clause.model.StandardJoinClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.clause.model.AliasedUnnestClause
import ch.ergon.dope.resolvable.expression.AliasedExpression
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.fromable.AliasedSelectClause
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.Fromable
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

interface ISelectOffsetClause : Clause

interface ISelectLimitClause : ISelectOffsetClause {
    fun offset(numberExpression: TypeExpression<NumberType>): SelectOffsetClause = SelectOffsetClause(numberExpression, this)
    fun offset(number: Number): SelectOffsetClause = offset(number.toNumberType())
}

interface ISelectOrderByClause : ISelectLimitClause {
    fun limit(numberExpression: TypeExpression<NumberType>): SelectLimitClause = SelectLimitClause(numberExpression, this)
    fun limit(number: Number): SelectLimitClause = limit(number.toNumberType())
}

interface ISelectGroupByClause : ISelectOrderByClause {
    fun orderBy(stringField: Field<StringType>): SelectOrderByClause = SelectOrderByClause(stringField, this)
    fun orderBy(stringField: Field<StringType>, orderByType: OrderByType): SelectOrderByTypeClause =
        SelectOrderByTypeClause(stringField, orderByType, this)
}

interface ISelectWhereClause : ISelectGroupByClause {
    fun groupBy(field: Field<out ValidType>, vararg fields: Field<out ValidType>): GroupByClause =
        GroupByClause(field, *fields, parentClause = this)
}

interface ISelectFromClause : ISelectWhereClause {
    fun where(whereExpression: TypeExpression<BooleanType>) = SelectWhereClause(whereExpression, this)
}

interface ISelectJoinClause : ISelectFromClause {
    fun join(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = StandardJoinClause(this, bucket, onCondition)
    fun join(bucket: Bucket, onKeys: Field<out ValidType>) = StandardJoinClause(this, bucket, onKeys)

    fun innerJoin(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = InnerJoinClause(this, bucket, onCondition)
    fun innerJoin(bucket: Bucket, onKeys: Field<out ValidType>) = InnerJoinClause(this, bucket, onKeys)

    fun leftJoin(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = LeftJoinClause(this, bucket, onCondition)
    fun leftJoin(bucket: Bucket, onKeys: Field<out ValidType>) = LeftJoinClause(this, bucket, onKeys)

    fun rightJoin(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = RightJoinClause(this, bucket, onCondition)
    fun rightJoin(bucket: Bucket, onKeys: Field<out ValidType>) = RightJoinClause(this, bucket, onKeys)
}

interface ISelectUnnestClause : ISelectJoinClause {
    fun unnest(field: Field<ArrayType<out ValidType>>) = UnnestClause(this, field)
    fun unnest(field: AliasedExpression<ArrayType<out ValidType>>) = AliasedUnnestClause(this, field)
}

interface ISelectClause : ISelectFromClause {
    fun from(fromable: Fromable) = FromClause(fromable, this)

    fun alias(alias: String) = AliasedSelectClause(alias, this)
}
