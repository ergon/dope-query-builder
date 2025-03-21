package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.Nestable
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.model.NestType.INNER_NEST
import ch.ergon.dope.resolvable.clause.model.NestType.LEFT_NEST
import ch.ergon.dope.resolvable.clause.model.NestType.NEST
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

private enum class NestType(override val type: String) : MergeType {
    NEST("NEST"),
    INNER_NEST("INNER NEST"),
    LEFT_NEST("LEFT NEST"),
}

class StandardNestClause<T : ValidType> : FromMergeable<T> {
    constructor(
        nestable: Nestable,
        onCondition: TypeExpression<BooleanType>,
        parentClause: ISelectFromClause<T>,
    ) : super(NEST, nestable, onCondition, parentClause = parentClause)

    constructor(
        nestable: Nestable,
        onKeys: Field<out ValidType>,
        parentClause: ISelectFromClause<T>,
    ) : super(NEST, nestable, onKeys, parentClause = parentClause)

    constructor(
        nestable: Nestable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
        parentClause: ISelectFromClause<T>,
    ) : super(NEST, nestable, onKey, forBucket, parentClause = parentClause)
}

class InnerNestClause<T : ValidType> : FromMergeable<T> {
    constructor(
        nestable: Nestable,
        onCondition: TypeExpression<BooleanType>,
        parentClause: ISelectFromClause<T>,
    ) : super(INNER_NEST, nestable, onCondition, parentClause = parentClause)

    constructor(
        nestable: Nestable,
        onKeys: Field<out ValidType>,
        parentClause: ISelectFromClause<T>,
    ) : super(INNER_NEST, nestable, onKeys, parentClause = parentClause)

    constructor(
        nestable: Nestable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
        parentClause: ISelectFromClause<T>,
    ) : super(INNER_NEST, nestable, onKey, forBucket, parentClause = parentClause)
}

class LeftNestClause<T : ValidType> : FromMergeable<T> {
    constructor(
        nestable: Nestable,
        onCondition: TypeExpression<BooleanType>,
        parentClause: ISelectFromClause<T>,
    ) : super(LEFT_NEST, nestable, onCondition, parentClause = parentClause)

    constructor(
        nestable: Nestable,
        onKeys: Field<out ValidType>,
        parentClause: ISelectFromClause<T>,
    ) : super(LEFT_NEST, nestable, onKeys, parentClause = parentClause)

    constructor(
        nestable: Nestable,
        onKey: Field<out ValidType>,
        forBucket: Bucket,
        parentClause: ISelectFromClause<T>,
    ) : super(LEFT_NEST, nestable, onKey, forBucket, parentClause = parentClause)
}
