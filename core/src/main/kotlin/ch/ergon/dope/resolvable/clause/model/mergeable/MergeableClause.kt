package ch.ergon.dope.resolvable.clause.model.mergeable

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

enum class OnType {
    ON,
    ON_KEYS,
    ON_KEY_FOR,
}

sealed interface MergeType

sealed interface MergeableClause<T : ValidType> : ISelectFromClause<T> {
    val mergeType: MergeType
    val mergeable: Resolvable
    val condition: TypeExpression<BooleanType>?
    val keys: TypeExpression<ArrayType<StringType>>?
    val key: TypeExpression<StringType>?
    val bucket: Bucket?
    val hashOrNestedLoopHint: HashOrNestedLoopHint?
    val keysOrIndexHint: KeysOrIndexHint?
    val parentClause: ISelectFromClause<T>

    val onType: OnType
        get() = when {
            condition != null -> OnType.ON
            keys != null || (key != null && bucket == null) -> OnType.ON_KEYS
            key != null && bucket != null -> OnType.ON_KEY_FOR
            else -> throw IllegalArgumentException("One of condition, keys or key must be provided for JoinClause.")
        }
}
