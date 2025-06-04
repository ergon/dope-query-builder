package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.Nestable
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.clause.model.AliasedUnnestClause
import ch.ergon.dope.resolvable.clause.model.DopeVariable
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.GroupByClause
import ch.ergon.dope.resolvable.clause.model.InnerJoinOnConditionClause
import ch.ergon.dope.resolvable.clause.model.InnerJoinOnKeyClause
import ch.ergon.dope.resolvable.clause.model.InnerJoinOnKeysClause
import ch.ergon.dope.resolvable.clause.model.InnerNestClause
import ch.ergon.dope.resolvable.clause.model.LeftJoinOnConditionClause
import ch.ergon.dope.resolvable.clause.model.LeftJoinOnKeyClause
import ch.ergon.dope.resolvable.clause.model.LeftJoinOnKeysClause
import ch.ergon.dope.resolvable.clause.model.LeftNestClause
import ch.ergon.dope.resolvable.clause.model.LetClause
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.clause.model.RightJoinClause
import ch.ergon.dope.resolvable.clause.model.SelectLimitClause
import ch.ergon.dope.resolvable.clause.model.SelectOffsetClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.SelectWhereClause
import ch.ergon.dope.resolvable.clause.model.StandardJoinOnConditionClause
import ch.ergon.dope.resolvable.clause.model.StandardJoinOnKeyClause
import ch.ergon.dope.resolvable.clause.model.StandardJoinOnKeysClause
import ch.ergon.dope.resolvable.clause.model.StandardNestClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.clause.model.WindowClause
import ch.ergon.dope.resolvable.clause.model.WindowDeclaration
import ch.ergon.dope.resolvable.clause.model.asWindowDeclaration
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.SelectExpression
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
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

interface ISelectWindowClause<T : ValidType> : ISelectOrderByClause<T> {
    fun orderBy(orderExpression: OrderExpression, vararg additionalOrderExpressions: OrderExpression) =
        SelectOrderByClause(orderExpression, *additionalOrderExpressions, parentClause = this)

    fun orderBy(expression: TypeExpression<out ValidType>, orderByType: OrderType? = null) = orderBy(OrderExpression(expression, orderByType))
}

interface ISelectGroupByClause<T : ValidType> : ISelectWindowClause<T> {
    fun referenceWindow(reference: String, windowDefinition: WindowDefinition? = null) =
        WindowClause(reference.asWindowDeclaration(windowDefinition), parentClause = this)

    fun referenceWindow(windowDeclaration: WindowDeclaration, vararg windowDeclarations: WindowDeclaration) =
        WindowClause(windowDeclaration, *windowDeclarations, parentClause = this)
}

interface ISelectWhereClause<T : ValidType> : ISelectGroupByClause<T> {
    fun groupBy(field: Field<out ValidType>, vararg fields: Field<out ValidType>) =
        GroupByClause(field, *fields, parentClause = this)
}

interface ISelectLetClause<T : ValidType> : ISelectWhereClause<T> {
    fun where(whereExpression: TypeExpression<BooleanType>) = SelectWhereClause(whereExpression, this)
}

interface ISelectFromClause<T : ValidType> : ISelectLetClause<T> {
    fun withVariables(dopeVariable: DopeVariable<out ValidType>, vararg dopeVariables: DopeVariable<out ValidType>) = LetClause(
        dopeVariable,
        *dopeVariables,
        parentClause = this,
    )

    fun join(
        joinable: Joinable,
        condition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = StandardJoinOnConditionClause(joinable, condition, hashOrNestedLoopHint, keysOrIndexHint, this)

    fun join(
        joinable: Joinable,
        keys: TypeExpression<ArrayType<StringType>>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = StandardJoinOnKeysClause(joinable, keys, hashOrNestedLoopHint, keysOrIndexHint, this)

    fun join(
        joinable: Joinable,
        keys: Collection<String>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = join(joinable, keys.toDopeType(), hashOrNestedLoopHint, keysOrIndexHint)

    fun join(
        joinable: Joinable,
        key: TypeExpression<StringType>,
        bucket: Bucket? = null,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = StandardJoinOnKeyClause(joinable, key, bucket, hashOrNestedLoopHint, keysOrIndexHint, this)

    fun join(
        joinable: Joinable,
        keys: String,
        bucket: Bucket? = null,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = join(joinable, keys.toDopeType(), bucket, hashOrNestedLoopHint, keysOrIndexHint)

    fun innerJoin(
        joinable: Joinable,
        condition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = InnerJoinOnConditionClause(joinable, condition, hashOrNestedLoopHint, keysOrIndexHint, this)

    fun innerJoin(
        joinable: Joinable,
        keys: TypeExpression<ArrayType<StringType>>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = InnerJoinOnKeysClause(joinable, keys, hashOrNestedLoopHint, keysOrIndexHint, this)

    fun innerJoin(
        joinable: Joinable,
        keys: Collection<String>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = innerJoin(joinable, keys.toDopeType(), hashOrNestedLoopHint, keysOrIndexHint)

    fun innerJoin(
        joinable: Joinable,
        key: TypeExpression<StringType>,
        bucket: Bucket? = null,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = InnerJoinOnKeyClause(joinable, key, bucket, hashOrNestedLoopHint, keysOrIndexHint, this)

    fun innerJoin(
        joinable: Joinable,
        key: String,
        bucket: Bucket? = null,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = innerJoin(joinable, key.toDopeType(), bucket, hashOrNestedLoopHint, keysOrIndexHint)

    fun leftJoin(
        joinable: Joinable,
        condition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = LeftJoinOnConditionClause(joinable, condition, hashOrNestedLoopHint, keysOrIndexHint, this)

    fun leftJoin(
        joinable: Joinable,
        keys: TypeExpression<ArrayType<StringType>>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = LeftJoinOnKeysClause(joinable, keys, hashOrNestedLoopHint, keysOrIndexHint, this)

    fun leftJoin(
        joinable: Joinable,
        keys: Collection<String>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = leftJoin(joinable, keys.toDopeType(), hashOrNestedLoopHint, keysOrIndexHint)

    fun leftJoin(
        joinable: Joinable,
        key: TypeExpression<StringType>,
        bucket: Bucket? = null,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = LeftJoinOnKeyClause(joinable, key, bucket, hashOrNestedLoopHint, keysOrIndexHint, this)

    fun leftJoin(
        joinable: Joinable,
        key: String,
        bucket: Bucket? = null,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = leftJoin(joinable, key.toDopeType(), bucket, hashOrNestedLoopHint, keysOrIndexHint)

    fun rightJoin(
        joinable: Joinable,
        condition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
    ) = RightJoinClause(joinable, condition, hashOrNestedLoopHint, keysOrIndexHint, this)

    fun <U : ValidType> unnest(arrayField: Field<ArrayType<U>>) = UnnestClause(arrayField, this)
    fun <U : ValidType> unnest(aliasedArrayExpression: AliasedTypeExpression<ArrayType<U>>) =
        AliasedUnnestClause(aliasedArrayExpression, this)

    fun nest(
        nestable: Nestable,
        onCondition: TypeExpression<BooleanType>,
    ) = StandardNestClause(nestable, onCondition, parentClause = this)
    fun nest(
        nestable: Nestable,
        onKeys: Field<out ValidType>,
    ) = StandardNestClause(nestable, onKeys, parentClause = this)
    fun nest(
        nestable: Nestable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
    ) = StandardNestClause(nestable, onKey, forBucket, parentClause = this)

    fun innerNest(
        nestable: Nestable,
        onCondition: TypeExpression<BooleanType>,
    ) = InnerNestClause(nestable, onCondition, parentClause = this)
    fun innerNest(
        nestable: Nestable,
        onKeys: Field<out ValidType>,
    ) = InnerNestClause(nestable, onKeys, parentClause = this)
    fun innerNest(
        nestable: Nestable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
    ) = InnerNestClause(nestable, onKey, forBucket, parentClause = this)

    fun leftNest(
        nestable: Nestable,
        onCondition: TypeExpression<BooleanType>,
    ) = LeftNestClause(nestable, onCondition, parentClause = this)
    fun leftNest(
        nestable: Nestable,
        onKeys: Field<out ValidType>,
    ) = LeftNestClause(nestable, onKeys, parentClause = this)
    fun leftNest(
        nestable: Nestable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
    ) = LeftNestClause(nestable, onKey, forBucket, parentClause = this)
}

interface ISelectClause<T : ValidType> : ISelectFromClause<T> {
    fun from(fromable: Fromable) = FromClause(fromable, this)
}
