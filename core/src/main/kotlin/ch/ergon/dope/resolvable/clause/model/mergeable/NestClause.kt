package ch.ergon.dope.resolvable.clause.model.mergeable

import ch.ergon.dope.resolvable.Nestable
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.clause.model.mergeable.NestType.INNER_NEST
import ch.ergon.dope.resolvable.clause.model.mergeable.NestType.LEFT_NEST
import ch.ergon.dope.resolvable.clause.model.mergeable.NestType.NEST
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.keyspace.Keyspace
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

enum class NestType : MergeType {
    NEST,
    INNER_NEST,
    LEFT_NEST,
}

data class StandardNestOnConditionClause<T : ValidType>(
    val nestable: Nestable,
    override val condition: TypeExpression<BooleanType>,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = NEST
    override val mergeable: Resolvable = nestable
    override val keys: TypeExpression<ArrayType<StringType>>? = null
    override val key: TypeExpression<StringType>? = null
    override val keyspace: Keyspace? = null
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null
    override val keysOrIndexHint: KeysOrIndexHint? = null
}

data class StandardNestOnKeysClause<T : ValidType>(
    val nestable: Nestable,
    override val keys: TypeExpression<ArrayType<StringType>>,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = NEST
    override val mergeable: Resolvable = nestable
    override val condition: TypeExpression<BooleanType>? = null
    override val key: TypeExpression<StringType>? = null
    override val keyspace: Keyspace? = null
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null
    override val keysOrIndexHint: KeysOrIndexHint? = null
}

data class StandardNestOnKeyClause<T : ValidType>(
    val nestable: Nestable,
    override val key: TypeExpression<StringType>,
    override val keyspace: Keyspace? = null,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = NEST
    override val mergeable: Resolvable = nestable
    override val condition: TypeExpression<BooleanType>? = null
    override val keys: TypeExpression<ArrayType<StringType>>? = null
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null
    override val keysOrIndexHint: KeysOrIndexHint? = null
}

data class InnerNestOnConditionClause<T : ValidType>(
    val nestable: Nestable,
    override val condition: TypeExpression<BooleanType>,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = INNER_NEST
    override val mergeable: Resolvable = nestable
    override val keys: TypeExpression<ArrayType<StringType>>? = null
    override val key: TypeExpression<StringType>? = null
    override val keyspace: Keyspace? = null
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null
    override val keysOrIndexHint: KeysOrIndexHint? = null
}

data class InnerNestOnKeysClause<T : ValidType>(
    val nestable: Nestable,
    override val keys: TypeExpression<ArrayType<StringType>>,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = INNER_NEST
    override val mergeable: Resolvable = nestable
    override val condition: TypeExpression<BooleanType>? = null
    override val key: TypeExpression<StringType>? = null
    override val keyspace: Keyspace? = null
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null
    override val keysOrIndexHint: KeysOrIndexHint? = null
}

data class InnerNestOnKeyClause<T : ValidType>(
    val nestable: Nestable,
    override val key: TypeExpression<StringType>,
    override val keyspace: Keyspace? = null,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = INNER_NEST
    override val mergeable: Resolvable = nestable
    override val condition: TypeExpression<BooleanType>? = null
    override val keys: TypeExpression<ArrayType<StringType>>? = null
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null
    override val keysOrIndexHint: KeysOrIndexHint? = null
}

data class LeftNestOnConditionClause<T : ValidType>(
    val nestable: Nestable,
    override val condition: TypeExpression<BooleanType>,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = LEFT_NEST
    override val mergeable: Resolvable = nestable
    override val keys: TypeExpression<ArrayType<StringType>>? = null
    override val key: TypeExpression<StringType>? = null
    override val keyspace: Keyspace? = null
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null
    override val keysOrIndexHint: KeysOrIndexHint? = null
}

data class LeftNestOnKeysClause<T : ValidType>(
    val nestable: Nestable,
    override val keys: TypeExpression<ArrayType<StringType>>,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = LEFT_NEST
    override val mergeable: Resolvable = nestable
    override val condition: TypeExpression<BooleanType>? = null
    override val key: TypeExpression<StringType>? = null
    override val keyspace: Keyspace? = null
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null
    override val keysOrIndexHint: KeysOrIndexHint? = null
}

data class LeftNestOnKeyClause<T : ValidType>(
    val nestable: Nestable,
    override val key: TypeExpression<StringType>,
    override val keyspace: Keyspace? = null,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = LEFT_NEST
    override val mergeable: Resolvable = nestable
    override val condition: TypeExpression<BooleanType>? = null
    override val keys: TypeExpression<ArrayType<StringType>>? = null
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null
    override val keysOrIndexHint: KeysOrIndexHint? = null
}
