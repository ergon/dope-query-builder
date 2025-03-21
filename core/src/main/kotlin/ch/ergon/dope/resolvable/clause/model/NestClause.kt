package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.Nestable
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.model.NestType.INNER_NEST
import ch.ergon.dope.resolvable.clause.model.NestType.LEFT_NEST
import ch.ergon.dope.resolvable.clause.model.NestType.NEST
import ch.ergon.dope.resolvable.clause.model.OnType.ON
import ch.ergon.dope.resolvable.clause.model.OnType.ON_KEYS
import ch.ergon.dope.resolvable.clause.model.OnType.ON_KEY_FOR
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

private enum class NestType(val type: String) {
    NEST("NEST"),
    INNER_NEST("INNER NEST"),
    LEFT_NEST("LEFT NEST"),
}

sealed class NestClause<T : ValidType> : ISelectFromClause<T> {
    private val nestType: NestType
    private val nestable: Nestable
    private val onCondition: TypeExpression<BooleanType>?
    private val onKeys: Field<out ValidType>?
    private val onKey: Field<out ValidType>?
    private val forBucket: Bucket?
    private val parentClause: ISelectFromClause<T>
    private val onType: OnType

    // ON (ANSI)
    constructor(
        nestType: NestType, // nest type
        nestable: Nestable, // nest rhs
        onCondition: TypeExpression<BooleanType>, // nest predicate
        parentClause: ISelectFromClause<T>, // preceding lhs
    ) {
        this.onType = ON
        this.nestable = nestable
        this.nestType = nestType
        this.onCondition = onCondition
        this.parentClause = parentClause
        this.onKeys = null
        this.onKey = null
        this.forBucket = null
    }

    // ON KEY (lookup)
    constructor(
        nestType: NestType, // nest type
        nestable: Nestable, // nest rhs
        onKeys: Field<out ValidType>, // nest predicate
        parentClause: ISelectFromClause<T>, // preceding lhs
    ) {
        this.onType = ON_KEYS
        this.nestType = nestType
        this.nestable = nestable
        this.onKeys = onKeys
        this.parentClause = parentClause
        this.onCondition = null
        this.onKey = null
        this.forBucket = null
    }

    // ON KEY ... FOR (index)
    constructor(
        nestType: NestType, // nest type
        nestable: Nestable, // nest rhs
        onKey: Field<out ValidType>, // nest predicate
        forBucket: Bucket, // predicate for
        parentClause: ISelectFromClause<T>, // preceding lhs
    ) {
        this.onType = ON_KEY_FOR
        this.nestType = nestType
        this.nestable = nestable
        this.onKey = onKey
        this.forBucket = forBucket
        this.parentClause = parentClause
        this.onCondition = null
        this.onKeys = null
    }

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val nestableDopeQuery = when (nestable) {
            is AliasedBucket -> nestable.asBucketDefinition().toDopeQuery(manager)
            is AliasedSelectClause<*> -> nestable.asAliasedSelectClauseDefinition().toDopeQuery(manager)
            else -> nestable.toDopeQuery(manager)
        }

        val nestQueryString = "${parentDopeQuery.queryString} ${nestType.type} ${nestableDopeQuery.queryString}"
        val nestParameters = parentDopeQuery.parameters.merge(nestableDopeQuery.parameters)

        return when (onType) {
            ON -> {
                val onConditionDopeQuery = onCondition?.toDopeQuery(manager)
                DopeQuery(
                    queryString = "$nestQueryString ON ${onConditionDopeQuery?.queryString}",
                    parameters = nestParameters.merge(onConditionDopeQuery?.parameters),
                )
            }

            ON_KEYS -> {
                val onKeysDopeQuery = onKeys?.toDopeQuery(manager)
                DopeQuery(
                    queryString = "$nestQueryString ON KEYS ${onKeysDopeQuery?.queryString}",
                    parameters = nestParameters.merge(onKeysDopeQuery?.parameters),
                )
            }

            ON_KEY_FOR -> {
                val onKeyDopeQuery = onKey?.toDopeQuery(manager)
                val forBucketDopeQuery = forBucket?.toDopeQuery(manager)
                DopeQuery(
                    queryString = "$nestQueryString ON KEY ${onKeyDopeQuery?.queryString} " +
                        "FOR ${forBucketDopeQuery?.queryString}",
                    parameters = nestParameters.merge(onKeyDopeQuery?.parameters),
                )
            }
        }
    }
}

// NEST
class StandardNestClause<T : ValidType> : NestClause<T> {
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

// NEST INNER
class InnerNestClause<T : ValidType> : NestClause<T> {
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

// NEST LEFT
class LeftNestClause<T : ValidType> : NestClause<T> {
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
