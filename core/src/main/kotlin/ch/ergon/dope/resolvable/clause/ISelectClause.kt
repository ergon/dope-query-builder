package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.clause.model.AliasedUnnestClause
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
import ch.ergon.dope.resolvable.clause.model.SelectUseKeysClause.Companion.SelectUseKeysClause
import ch.ergon.dope.resolvable.clause.model.SelectWhereClause
import ch.ergon.dope.resolvable.clause.model.StandardJoinClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.expression.AliasedExpression
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
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
    fun offset(number: Number): SelectOffsetClause = offset(number.toDopeType())
}

interface ISelectOrderByClause : ISelectLimitClause {
    fun limit(numberExpression: TypeExpression<NumberType>): SelectLimitClause = SelectLimitClause(numberExpression, this)
    fun limit(number: Number): SelectLimitClause = limit(number.toDopeType())
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

interface ISelectUseKeysClause : ISelectWhereClause {
    fun where(whereExpression: TypeExpression<BooleanType>) = SelectWhereClause(whereExpression, this)
}

interface ISelectFromClause : ISelectUseKeysClause {
    fun useKeys(key: TypeExpression<StringType>) = SelectUseKeysClause(key, this)

    // JvmName annotation in interfaces is currently not supported. https://youtrack.jetbrains.com/issue/KT-20068
    @Suppress("INAPPLICABLE_JVM_NAME")
    @JvmName("useKeysArray")
    fun useKeys(keys: TypeExpression<ArrayType<StringType>>) = SelectUseKeysClause(keys, this)
}

interface ISelectJoinClause : ISelectFromClause {
    fun join(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = StandardJoinClause(bucket, onCondition, this)
    fun join(bucket: Bucket, onKeys: Field<out ValidType>) = StandardJoinClause(bucket, onKeys, this)
    fun join(bucket: Bucket, onKey: Field<out ValidType>, forBucket: Bucket) = StandardJoinClause(bucket, onKey, forBucket, this)

    fun innerJoin(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = InnerJoinClause(bucket, onCondition, this)
    fun innerJoin(bucket: Bucket, onKeys: Field<out ValidType>) = InnerJoinClause(bucket, onKeys, this)
    fun innerJoin(bucket: Bucket, onKey: Field<out ValidType>, forBucket: Bucket) = InnerJoinClause(bucket, onKey, forBucket, this)

    fun leftJoin(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = LeftJoinClause(bucket, onCondition, this)
    fun leftJoin(bucket: Bucket, onKeys: Field<out ValidType>) = LeftJoinClause(bucket, onKeys, this)
    fun leftJoin(bucket: Bucket, onKey: Field<out ValidType>, forBucket: Bucket) = LeftJoinClause(bucket, onKey, forBucket, this)

    fun rightJoin(bucket: Bucket, onCondition: TypeExpression<BooleanType>) = RightJoinClause(bucket, onCondition, this)
}

interface ISelectUnnestClause : ISelectJoinClause {
    fun <T : ValidType> unnest(arrayField: Field<ArrayType<T>>) = UnnestClause(arrayField, this)
    fun <T : ValidType> unnest(aliasedArrayExpression: AliasedExpression<ArrayType<T>>) = AliasedUnnestClause(aliasedArrayExpression, this)
}

interface ISelectClause : ISelectFromClause {
    fun from(fromable: Fromable) = FromClause(fromable, this)

    fun alias(alias: String) = AliasedSelectClause(alias, this)
}
