package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.Nestable
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.model.NestType.INNER_NEST
import ch.ergon.dope.resolvable.clause.model.NestType.LEFT_NEST
import ch.ergon.dope.resolvable.clause.model.NestType.NEST
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

private enum class NestType(override val type: String) : MergeType {
    NEST("NEST"),
    INNER_NEST("INNER NEST"),
    LEFT_NEST("LEFT NEST"),
}

class StandardNestOnConditionClause<T : ValidType>(
    nestable: Nestable,
    condition: TypeExpression<BooleanType>,
    parentClause: ISelectFromClause<T>,
) : FromMergeable<T>(
    mergeType = NEST,
    mergeable = nestable,
    condition = condition,
    parentClause = parentClause,
)

class StandardNestOnKeysClause<T : ValidType>(
    nestable: Nestable,
    keys: TypeExpression<ArrayType<StringType>>,
    parentClause: ISelectFromClause<T>,
) : FromMergeable<T>(
    mergeType = NEST,
    mergeable = nestable,
    keys = keys,
    parentClause = parentClause,
)

class StandardNestOnKeyClause<T : ValidType>(
    nestable: Nestable,
    key: TypeExpression<StringType>,
    bucket: Bucket? = null,
    parentClause: ISelectFromClause<T>,
) : FromMergeable<T>(
    mergeType = NEST,
    mergeable = nestable,
    key = key,
    bucket = bucket,
    parentClause = parentClause,
)

class InnerNestOnConditionClause<T : ValidType>(
    nestable: Nestable,
    condition: TypeExpression<BooleanType>,
    parentClause: ISelectFromClause<T>,
) : FromMergeable<T>(
    mergeType = INNER_NEST,
    mergeable = nestable,
    condition = condition,
    parentClause = parentClause,
)

class InnerNestOnKeysClause<T : ValidType>(
    nestable: Nestable,
    keys: TypeExpression<ArrayType<StringType>>,
    parentClause: ISelectFromClause<T>,
) : FromMergeable<T>(
    mergeType = INNER_NEST,
    mergeable = nestable,
    keys = keys,
    parentClause = parentClause,
)

class InnerNestOnKeyClause<T : ValidType>(
    nestable: Nestable,
    key: TypeExpression<StringType>,
    bucket: Bucket? = null,
    parentClause: ISelectFromClause<T>,
) : FromMergeable<T>(
    mergeType = INNER_NEST,
    mergeable = nestable,
    key = key,
    bucket = bucket,
    parentClause = parentClause,
)

class LeftNestOnConditionClause<T : ValidType>(
    nestable: Nestable,
    condition: TypeExpression<BooleanType>,
    parentClause: ISelectFromClause<T>,
) : FromMergeable<T>(
    mergeType = LEFT_NEST,
    mergeable = nestable,
    condition = condition,
    parentClause = parentClause,
)

class LeftNestOnKeysClause<T : ValidType>(
    nestable: Nestable,
    keys: TypeExpression<ArrayType<StringType>>,
    parentClause: ISelectFromClause<T>,
) : FromMergeable<T>(
    mergeType = LEFT_NEST,
    mergeable = nestable,
    keys = keys,
    parentClause = parentClause,
)

class LeftNestOnKeyClause<T : ValidType>(
    nestable: Nestable,
    key: TypeExpression<StringType>,
    bucket: Bucket? = null,
    parentClause: ISelectFromClause<T>,
) : FromMergeable<T>(
    mergeType = LEFT_NEST,
    mergeable = nestable,
    key = key,
    bucket = bucket,
    parentClause = parentClause,
)
