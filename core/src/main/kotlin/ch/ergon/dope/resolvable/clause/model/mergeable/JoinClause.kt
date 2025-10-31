package ch.ergon.dope.resolvable.clause.model.mergeable

import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.keyspace.KeySpace
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

enum class JoinType : MergeType {
    JOIN,
    LEFT_JOIN,
    INNER_JOIN,
    RIGHT_JOIN,
}

data class StandardJoinOnConditionClause<T : ValidType>(
    override val mergeable: Joinable,
    override val condition: TypeExpression<BooleanType>,
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    override val keysOrIndexHint: KeysOrIndexHint? = null,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType = JoinType.JOIN
    override val keys: TypeExpression<ArrayType<StringType>>? = null
    override val key: TypeExpression<StringType>? = null
    override val keyspace: KeySpace? = null
}

data class StandardJoinOnKeysClause<T : ValidType>(
    override val mergeable: Joinable,
    override val keys: TypeExpression<ArrayType<StringType>>,
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    override val keysOrIndexHint: KeysOrIndexHint? = null,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType = JoinType.JOIN
    override val condition: TypeExpression<BooleanType>? = null
    override val key: TypeExpression<StringType>? = null
    override val keyspace: KeySpace? = null
}

data class StandardJoinOnKeyClause<T : ValidType>(
    override val mergeable: Joinable,
    override val key: TypeExpression<StringType>,
    override val keyspace: KeySpace? = null,
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    override val keysOrIndexHint: KeysOrIndexHint? = null,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = JoinType.JOIN
    override val condition: TypeExpression<BooleanType>? = null
    override val keys: TypeExpression<ArrayType<StringType>>? = null
}

data class LeftJoinOnConditionClause<T : ValidType>(
    override val mergeable: Joinable,
    override val condition: TypeExpression<BooleanType>,
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    override val keysOrIndexHint: KeysOrIndexHint? = null,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = JoinType.LEFT_JOIN
    override val keys: TypeExpression<ArrayType<StringType>>? = null
    override val key: TypeExpression<StringType>? = null
    override val keyspace: KeySpace? = null
}

data class LeftJoinOnKeysClause<T : ValidType>(
    override val mergeable: Joinable,
    override val keys: TypeExpression<ArrayType<StringType>>,
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    override val keysOrIndexHint: KeysOrIndexHint? = null,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = JoinType.LEFT_JOIN
    override val condition: TypeExpression<BooleanType>? = null
    override val key: TypeExpression<StringType>? = null
    override val keyspace: KeySpace? = null
}

data class LeftJoinOnKeyClause<T : ValidType>(
    override val mergeable: Joinable,
    override val key: TypeExpression<StringType>,
    override val keyspace: KeySpace? = null,
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    override val keysOrIndexHint: KeysOrIndexHint? = null,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = JoinType.LEFT_JOIN
    override val condition: TypeExpression<BooleanType>? = null
    override val keys: TypeExpression<ArrayType<StringType>>? = null
}

data class InnerJoinOnConditionClause<T : ValidType>(
    override val mergeable: Joinable,
    override val condition: TypeExpression<BooleanType>,
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    override val keysOrIndexHint: KeysOrIndexHint? = null,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = JoinType.INNER_JOIN
    override val keys: TypeExpression<ArrayType<StringType>>? = null
    override val key: TypeExpression<StringType>? = null
    override val keyspace: KeySpace? = null
}

data class InnerJoinOnKeysClause<T : ValidType>(
    override val mergeable: Joinable,
    override val keys: TypeExpression<ArrayType<StringType>>,
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    override val keysOrIndexHint: KeysOrIndexHint? = null,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = JoinType.INNER_JOIN
    override val condition: TypeExpression<BooleanType>? = null
    override val key: TypeExpression<StringType>? = null
    override val keyspace: KeySpace? = null
}

data class InnerJoinOnKeyClause<T : ValidType>(
    override val mergeable: Joinable,
    override val key: TypeExpression<StringType>,
    override val keyspace: KeySpace? = null,
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    override val keysOrIndexHint: KeysOrIndexHint? = null,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = JoinType.INNER_JOIN
    override val condition: TypeExpression<BooleanType>? = null
    override val keys: TypeExpression<ArrayType<StringType>>? = null
}

data class RightJoinClause<T : ValidType>(
    override val mergeable: Joinable,
    override val condition: TypeExpression<BooleanType>,
    override val hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    override val keysOrIndexHint: KeysOrIndexHint? = null,
    override val parentClause: ISelectFromClause<T>,
) : MergeableClause<T> {
    override val mergeType: MergeType = JoinType.RIGHT_JOIN
    override val keys: TypeExpression<ArrayType<StringType>>? = null
    override val key: TypeExpression<StringType>? = null
    override val keyspace: KeySpace? = null
}
