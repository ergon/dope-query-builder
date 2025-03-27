package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.clause.model.AliasedUnnestClause
import ch.ergon.dope.resolvable.clause.model.DopeVariable
import ch.ergon.dope.resolvable.clause.model.FromClause
import ch.ergon.dope.resolvable.clause.model.GroupByClause
import ch.ergon.dope.resolvable.clause.model.InnerJoinClause
import ch.ergon.dope.resolvable.clause.model.LeftJoinClause
import ch.ergon.dope.resolvable.clause.model.LetClause
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.clause.model.RightJoinClause
import ch.ergon.dope.resolvable.clause.model.SelectLimitClause
import ch.ergon.dope.resolvable.clause.model.SelectOffsetClause
import ch.ergon.dope.resolvable.clause.model.SelectOrderByClause
import ch.ergon.dope.resolvable.clause.model.SelectWhereClause
import ch.ergon.dope.resolvable.clause.model.StandardJoinClause
import ch.ergon.dope.resolvable.clause.model.UnnestClause
import ch.ergon.dope.resolvable.clause.model.WindowClause
import ch.ergon.dope.resolvable.clause.model.WindowDeclaration
import ch.ergon.dope.resolvable.clause.model.asWindowDeclaration
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.SelectExpression
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
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
    fun <U : ValidType> unnest(aliasedArrayExpression: ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression<ArrayType<U>>) =
        AliasedUnnestClause(aliasedArrayExpression, this)
}

interface ISelectClause<T : ValidType> : ISelectFromClause<T> {
    fun from(fromable: Fromable) = FromClause(fromable, this)
}
