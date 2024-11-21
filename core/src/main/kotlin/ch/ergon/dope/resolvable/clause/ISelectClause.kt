package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.clause.model.AliasedUnnestClause
import ch.ergon.dope.resolvable.clause.model.DopeVariable
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.GroupByClause
import ch.ergon.dope.resolvable.clause.model.InnerJoinClause
import ch.ergon.dope.resolvable.clause.model.LeftJoinClause
import ch.ergon.dope.resolvable.clause.model.LetClause
import ch.ergon.dope.resolvable.clause.model.OrderByType
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.RightJoinClause
import ch.ergon.dope.resolvable.clause.model.SelectLimitClause
import ch.ergon.dope.resolvable.clause.model.SelectOffsetClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
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
import ch.ergon.dope.validtype.ValidType

interface ISelectOffsetClause<T : ValidType> : Clause {
    fun alias(alias: String): AliasedSelectClause<T> = AliasedSelectClause(alias, this)
    fun asExpression(): SelectExpression<T> = SelectExpression(this)
}

interface ISelectLimitClause<T : ValidType> : ISelectOffsetClause<T> {
    fun offset(numberExpression: TypeExpression<NumberType>) = SelectOffsetClause(numberExpression, this)
    fun offset(number: Number) = offset(number.toDopeType())
}

interface ISelectOrderByClause<T : ValidType> : ISelectLimitClause<T> {
    fun limit(numberExpression: TypeExpression<NumberType>) = SelectLimitClause(numberExpression, this)
    fun limit(number: Number) = limit(number.toDopeType())
}

interface ISelectGroupByClause<T : ValidType> : ISelectOrderByClause<T> {
    fun orderBy(expression: TypeExpression<out ValidType>, orderByType: OrderByType? = null) = SelectOrderByClause(
        OrderExpression(expression, orderByType),
        parentClause = this,
    )
}

interface ISelectWhereClause<T : ValidType> : ISelectGroupByClause<T> {
    fun groupBy(field: Field<out ValidType>, vararg fields: Field<out ValidType>) =
        GroupByClause(field, *fields, parentClause = this)
}

interface ISelectFromClause<T : ValidType> : ISelectWhereClause<T> {
    fun where(whereExpression: TypeExpression<BooleanType>) = SelectWhereClause(whereExpression, this)
}

interface ISelectLetClause<T : ValidType> : ISelectFromClause<T> {
    fun withVariables(dopeVariable: DopeVariable<out ValidType>, vararg dopeVariables: DopeVariable<out ValidType>) = LetClause(
        dopeVariable,
        *dopeVariables,
        parentClause = this,
    )
}

interface ISelectJoinClause<T : ValidType> : ISelectLetClause<T> {
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
}

interface ISelectUnnestClause<T : ValidType> : ISelectJoinClause<T> {
    fun <U : ValidType> unnest(arrayField: Field<ArrayType<U>>) = UnnestClause(arrayField, this)
    fun <U : ValidType> unnest(aliasedArrayExpression: AliasedExpression<ArrayType<U>>) =
        AliasedUnnestClause(aliasedArrayExpression, this)
}

interface ISelectClause<T : ValidType> : ISelectFromClause<T> {
    fun from(fromable: Fromable) = FromClause(fromable, this)
}
