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
import ch.ergon.dope.resolvable.clause.model.SelectWhereClause
import ch.ergon.dope.resolvable.clause.model.StandardJoinClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.clause.model.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.model.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.expression.AliasedExpression
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.fromable.AliasedSelectClause
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.Fromable
import ch.ergon.dope.resolvable.fromable.Joinable
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

interface ISelectOffsetClause : Clause

interface ISelectLimitClause : ISelectOffsetClause {
    fun offset(numberExpression: TypeExpression<NumberType>) = SelectOffsetClause(numberExpression, this)
    fun offset(number: Number) = offset(number.toDopeType())
}

interface ISelectOrderByClause : ISelectLimitClause {
    fun limit(numberExpression: TypeExpression<NumberType>) = SelectLimitClause(numberExpression, this)
    fun limit(number: Number) = limit(number.toDopeType())
}

interface ISelectGroupByClause : ISelectOrderByClause {
    fun orderBy(stringField: Field<StringType>) = SelectOrderByClause(stringField, this)
    fun orderBy(stringField: Field<StringType>, orderByType: OrderByType) =
        SelectOrderByTypeClause(stringField, orderByType, this)
}

interface ISelectWhereClause : ISelectGroupByClause {
    fun groupBy(field: Field<out ValidType>, vararg fields: Field<out ValidType>) =
        GroupByClause(field, *fields, parentClause = this)
}

interface ISelectFromClause : ISelectWhereClause {
    fun where(whereExpression: TypeExpression<BooleanType>) = SelectWhereClause(whereExpression, this)
}

interface ISelectJoinClause : ISelectFromClause {
    fun join(
        joinable: Joinable,
        onCondition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = StandardJoinClause(joinable, onCondition, hashOrNestedLoopHint, keysOrIndexHint, this)
    fun join(
        joinable: Joinable,
        onKeys: Field<out ValidType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = StandardJoinClause(joinable, onKeys, hashOrNestedLoopHint, keysOrIndexHint, this)
    fun join(
        joinable: Joinable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = StandardJoinClause(joinable, onKey, forBucket, hashOrNestedLoopHint, keysOrIndexHint, this)

    fun innerJoin(
        joinable: Joinable,
        onCondition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = InnerJoinClause(joinable, onCondition, hashOrNestedLoopHint, keysOrIndexHint, this)
    fun innerJoin(
        joinable: Joinable,
        onKeys: Field<out ValidType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = InnerJoinClause(joinable, onKeys, hashOrNestedLoopHint, keysOrIndexHint, this)
    fun innerJoin(
        joinable: Joinable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = InnerJoinClause(joinable, onKey, forBucket, hashOrNestedLoopHint, keysOrIndexHint, this)

    fun leftJoin(
        joinable: Joinable,
        onCondition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = LeftJoinClause(joinable, onCondition, hashOrNestedLoopHint, keysOrIndexHint, this)
    fun leftJoin(
        joinable: Joinable,
        onKeys: Field<out ValidType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = LeftJoinClause(joinable, onKeys, hashOrNestedLoopHint, keysOrIndexHint, this)
    fun leftJoin(
        joinable: Joinable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = LeftJoinClause(joinable, onKey, forBucket, hashOrNestedLoopHint, keysOrIndexHint, this)

    fun rightJoin(
        joinable: Joinable,
        onCondition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = RightJoinClause(joinable, onCondition, hashOrNestedLoopHint, keysOrIndexHint, this)

    fun alias(alias: String) = AliasedSelectClause(alias, this)
}

interface ISelectUnnestClause : ISelectJoinClause {
    fun <T : ValidType> unnest(arrayField: Field<ArrayType<T>>) = UnnestClause(arrayField, this)
    fun <T : ValidType> unnest(aliasedArrayExpression: AliasedExpression<ArrayType<T>>) =
        AliasedUnnestClause(aliasedArrayExpression, this)
}

interface ISelectClause : ISelectFromClause {
    fun from(fromable: Fromable) = FromClause(fromable, this)
}
