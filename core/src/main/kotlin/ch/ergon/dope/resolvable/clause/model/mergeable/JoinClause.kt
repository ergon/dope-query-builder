package ch.ergon.dope.resolvable.clause.model.mergeable

import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

private enum class JoinType(override val type: String) : MergeType {
    JOIN("JOIN"),
    LEFT_JOIN("LEFT JOIN"),
    INNER_JOIN("INNER JOIN"),
    RIGHT_JOIN("RIGHT JOIN"),
}

class StandardJoinOnConditionClause<T : ValidType>(
    mergeable: Joinable,
    condition: TypeExpression<BooleanType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : MergeableClause<T>(
    mergeType = JoinType.JOIN,
    mergeable = mergeable,
    condition = condition,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class StandardJoinOnKeysClause<T : ValidType>(
    mergeable: Joinable,
    keys: TypeExpression<ArrayType<StringType>>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : MergeableClause<T>(
    mergeType = JoinType.JOIN,
    mergeable = mergeable,
    keys = keys,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class StandardJoinOnKeyClause<T : ValidType>(
    mergeable: Joinable,
    key: TypeExpression<StringType>,
    bucket: Bucket? = null,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : MergeableClause<T>(
    mergeType = JoinType.JOIN,
    mergeable = mergeable,
    key = key,
    bucket = bucket,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class LeftJoinOnConditionClause<T : ValidType>(
    mergeable: Joinable,
    condition: TypeExpression<BooleanType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : MergeableClause<T>(
    mergeType = JoinType.LEFT_JOIN,
    mergeable = mergeable,
    condition = condition,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class LeftJoinOnKeysClause<T : ValidType>(
    mergeable: Joinable,
    keys: TypeExpression<ArrayType<StringType>>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : MergeableClause<T>(
    mergeType = JoinType.LEFT_JOIN,
    mergeable = mergeable,
    keys = keys,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class LeftJoinOnKeyClause<T : ValidType>(
    mergeable: Joinable,
    key: TypeExpression<StringType>,
    bucket: Bucket? = null,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : MergeableClause<T>(
    mergeType = JoinType.LEFT_JOIN,
    mergeable = mergeable,
    key = key,
    bucket = bucket,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class InnerJoinOnConditionClause<T : ValidType>(
    mergeable: Joinable,
    condition: TypeExpression<BooleanType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : MergeableClause<T>(
    mergeType = JoinType.INNER_JOIN,
    mergeable = mergeable,
    condition = condition,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class InnerJoinOnKeysClause<T : ValidType>(
    mergeable: Joinable,
    keys: TypeExpression<ArrayType<StringType>>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : MergeableClause<T>(
    mergeType = JoinType.INNER_JOIN,
    mergeable = mergeable,
    keys = keys,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class InnerJoinOnKeyClause<T : ValidType>(
    mergeable: Joinable,
    key: TypeExpression<StringType>,
    bucket: Bucket? = null,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : MergeableClause<T>(
    mergeType = JoinType.INNER_JOIN,
    mergeable = mergeable,
    key = key,
    bucket = bucket,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)

class RightJoinClause<T : ValidType>(
    mergeable: Joinable,
    condition: TypeExpression<BooleanType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : MergeableClause<T>(
    mergeType = JoinType.RIGHT_JOIN,
    mergeable = mergeable,
    condition = condition,
    hashOrNestedLoopHint = hashOrNestedLoopHint,
    keysOrIndexHint = keysOrIndexHint,
    parentClause = parentClause,
)
