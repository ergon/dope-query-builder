package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint
import ch.ergon.dope.resolvable.clause.joinHint.KeysOrIndexHint
import ch.ergon.dope.resolvable.clause.model.JoinType.INNER_JOIN
import ch.ergon.dope.resolvable.clause.model.JoinType.JOIN
import ch.ergon.dope.resolvable.clause.model.JoinType.LEFT_JOIN
import ch.ergon.dope.resolvable.clause.model.JoinType.RIGHT_JOIN
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

enum class JoinType(override val type: String) : MergeType {
    JOIN("JOIN"),
    LEFT_JOIN("LEFT JOIN"),
    INNER_JOIN("INNER JOIN"),
    RIGHT_JOIN("RIGHT JOIN"),
}

class StandardJoinClause<T : ValidType> : FromMergeable<T> {
    constructor(
        joinable: Joinable,
        onCondition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
        parentClause: ISelectFromClause<T>,
    ) : super(JOIN, joinable, onCondition, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)

    constructor(
        joinable: Joinable,
        onKeys: Field<out ValidType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
        parentClause: ISelectFromClause<T>,
    ) : super(JOIN, joinable, onKeys, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)

    constructor(
        joinable: Joinable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
        parentClause: ISelectFromClause<T>,
    ) : super(JOIN, joinable, onKey, forBucket, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)
}

class LeftJoinClause<T : ValidType> : FromMergeable<T> {
    constructor(
        joinable: Joinable,
        onCondition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
        parentClause: ISelectFromClause<T>,
    ) : super(LEFT_JOIN, joinable, onCondition, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)

    constructor(
        joinable: Joinable,
        onKeys: Field<out ValidType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
        parentClause: ISelectFromClause<T>,
    ) : super(LEFT_JOIN, joinable, onKeys, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)

    constructor(
        joinable: Joinable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
        parentClause: ISelectFromClause<T>,
    ) : super(LEFT_JOIN, joinable, onKey, forBucket, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)
}

class InnerJoinClause<T : ValidType> : FromMergeable<T> {
    constructor(
        joinable: Joinable,
        onCondition: TypeExpression<BooleanType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
        parentClause: ISelectFromClause<T>,
    ) : super(INNER_JOIN, joinable, onCondition, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)

    constructor(
        joinable: Joinable,
        onKeys: Field<out ValidType>,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
        parentClause: ISelectFromClause<T>,
    ) : super(INNER_JOIN, joinable, onKeys, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)

    constructor(
        joinable: Joinable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
        hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
        keysOrIndexHint: KeysOrIndexHint? = null,
        parentClause: ISelectFromClause<T>,
    ) : super(INNER_JOIN, joinable, onKey, forBucket, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)
}

class RightJoinClause<T : ValidType>(
    joinable: Joinable,
    onCondition: TypeExpression<BooleanType>,
    hashOrNestedLoopHint: HashOrNestedLoopHint? = null,
    keysOrIndexHint: KeysOrIndexHint? = null,
    parentClause: ISelectFromClause<T>,
) : FromMergeable<T>(RIGHT_JOIN, joinable, onCondition, hashOrNestedLoopHint, keysOrIndexHint, parentClause = parentClause)
