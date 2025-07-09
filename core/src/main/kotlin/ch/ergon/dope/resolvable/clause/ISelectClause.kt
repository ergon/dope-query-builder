package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.QueryProvider
import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.Nestable
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.asterisk
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.clause.model.AliasedUnnestClause
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.GroupByClause
import ch.ergon.dope.resolvable.clause.model.LetClause
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.clause.model.SelectClause
import ch.ergon.dope.resolvable.clause.model.SelectDistinctClause
import ch.ergon.dope.resolvable.clause.model.SelectLimitClause
import ch.ergon.dope.resolvable.clause.model.SelectOffsetClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.SelectRawClause
import ch.ergon.dope.resolvable.clause.model.SelectWhereClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.clause.model.WindowClause
import ch.ergon.dope.resolvable.clause.model.WindowDeclaration
import ch.ergon.dope.resolvable.clause.model.asWindowDeclaration
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerJoinOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerJoinOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerJoinOnKeysClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerNestOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerNestOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.InnerNestOnKeysClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftJoinOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftJoinOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftJoinOnKeysClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftNestOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftNestOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.LeftNestOnKeysClause
import ch.ergon.dope.resolvable.clause.model.mergeable.RightJoinClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardJoinOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardJoinOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardJoinOnKeysClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardNestOnConditionClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardNestOnKeyClause
import ch.ergon.dope.resolvable.clause.model.mergeable.StandardNestOnKeysClause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.type.DopeVariable
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

    fun nest(nestable: Nestable, condition: TypeExpression<BooleanType>) =
        StandardNestOnConditionClause(nestable, condition, this)

    fun nest(nestable: Nestable, keys: TypeExpression<ArrayType<StringType>>) =
        StandardNestOnKeysClause(nestable, keys, this)

    fun nest(nestable: Nestable, keys: Collection<String>) =
        nest(nestable, keys.toDopeType())

    fun nest(nestable: Nestable, key: TypeExpression<StringType>, bucket: Bucket? = null) =
        StandardNestOnKeyClause(nestable, key, bucket, this)

    fun nest(nestable: Nestable, key: String, bucket: Bucket? = null) =
        nest(nestable, key.toDopeType(), bucket)

    fun innerNest(nestable: Nestable, condition: TypeExpression<BooleanType>) =
        InnerNestOnConditionClause(nestable, condition, this)

    fun innerNest(nestable: Nestable, keys: TypeExpression<ArrayType<StringType>>) =
        InnerNestOnKeysClause(nestable, keys, this)

    fun innerNest(nestable: Nestable, keys: Collection<String>) =
        innerNest(nestable, keys.toDopeType())

    fun innerNest(nestable: Nestable, key: TypeExpression<StringType>, bucket: Bucket? = null) =
        InnerNestOnKeyClause(nestable, key, bucket, this)

    fun innerNest(nestable: Nestable, key: String, bucket: Bucket? = null) =
        innerNest(nestable, key.toDopeType(), bucket)

    fun leftNest(nestable: Nestable, condition: TypeExpression<BooleanType>) =
        LeftNestOnConditionClause(nestable, condition, this)

    fun leftNest(nestable: Nestable, keys: TypeExpression<ArrayType<StringType>>) =
        LeftNestOnKeysClause(nestable, keys, this)

    fun leftNest(nestable: Nestable, keys: Collection<String>) =
        leftNest(nestable, keys.toDopeType())

    fun leftNest(nestable: Nestable, key: TypeExpression<StringType>, bucket: Bucket? = null) =
        LeftNestOnKeyClause(nestable, key, bucket, this)

    fun leftNest(nestable: Nestable, key: String, bucket: Bucket? = null) =
        leftNest(nestable, key.toDopeType(), bucket)
}

interface ISelectClause<T : ValidType> : ISelectFromClause<T> {
    fun from(fromable: Fromable) = FromClause(fromable, this)
}

interface ISelectWithClause : QueryProvider, Resolvable {
    override fun select(expression: Selectable, vararg expressions: Selectable) = SelectClause(expression, *expressions, parentClause = this)

    override fun selectAsterisk() = SelectClause(asterisk(), parentClause = this)

    override fun selectDistinct(expression: Selectable, vararg expressions: Selectable) =
        SelectDistinctClause(expression, *expressions, parentClause = this)

    override fun <T : ValidType> selectRaw(expression: Expression<T>) = SelectRawClause(expression, parentClause = this)

    override fun selectFrom(fromable: Fromable) = selectAsterisk().from(fromable)
}
