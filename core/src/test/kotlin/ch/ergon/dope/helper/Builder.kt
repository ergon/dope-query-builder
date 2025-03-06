package ch.ergon.dope.helper

import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.bucket.UnaliasedBucket
import ch.ergon.dope.resolvable.clause.model.OrderByType
import ch.ergon.dope.resolvable.clause.model.OrderByType.ASC
import ch.ergon.dope.resolvable.clause.model.OrderExpression
import ch.ergon.dope.resolvable.expression.type.CaseClass
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.TRUE
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.SearchResult
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.clause.model.OrderType.ASC
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.CountAsteriskExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.TRUE
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.CaseClass
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.SearchResult
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.expression.windowfunction.NullsOrder
import ch.ergon.dope.resolvable.expression.windowfunction.OrderingTerm
import ch.ergon.dope.resolvable.expression.windowfunction.UnboundedPreceding
import ch.ergon.dope.resolvable.expression.windowfunction.WindowDefinition
import ch.ergon.dope.resolvable.expression.windowfunction.WindowFrameClause
import ch.ergon.dope.resolvable.expression.windowfunction.WindowFrameExclusion
import ch.ergon.dope.resolvable.expression.windowfunction.WindowFrameExtent
import ch.ergon.dope.resolvable.expression.windowfunction.WindowFrameType
import ch.ergon.dope.resolvable.expression.windowfunction.WindowFrameType.RANGE
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.UnaliasedBucket
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

fun someBucket(name: String = "someBucket") = UnaliasedBucket(name)

fun someNumberField(name: String = "numberField", bucket: Bucket = someBucket("")) = Field<NumberType>(name, getBucketName(bucket))

fun someStringField(name: String = "stringField", bucket: Bucket = someBucket("")) = Field<StringType>(name, getBucketName(bucket))

fun someBooleanField(name: String = "booleanField", bucket: Bucket = someBucket("")) = Field<BooleanType>(name, getBucketName(bucket))

fun someObjectField(name: String = "objectField", bucket: Bucket = someBucket("")) = Field<ObjectType>(name, getBucketName(bucket))

fun someAnyTypeField(name: String = "anyTypeField", bucket: Bucket = someBucket("")) = Field<ValidType>(name, getBucketName(bucket))

fun someBooleanExpression() = TRUE

fun someNumberArrayField(name: String = "numberArrayField", bucket: Bucket = someBucket("")) =
    Field<ArrayType<NumberType>>(name, getBucketName(bucket))

fun someStringArrayField(name: String = "stringArrayField", bucket: Bucket = someBucket("")) =
    Field<ArrayType<StringType>>(name, getBucketName(bucket))

fun someBooleanArrayField(name: String = "booleanArrayField", bucket: Bucket = someBucket("")) =
    Field<ArrayType<BooleanType>>(name, getBucketName(bucket))

fun someObjectArrayField(name: String = "objectArrayField", bucket: Bucket = someBucket("")) =
    Field<ArrayType<ObjectType>>(name, getBucketName(bucket))

fun someAnyTypeArrayField(name: String = "anyTypeArrayField", bucket: Bucket = someBucket("")) =
    Field<ArrayType<ValidType>>(name, getBucketName(bucket))

fun someNumber(value: Number = 5) = value

fun someInt(value: Int = 5) = value

fun someString(value: String = "someString") = value

fun someBoolean(value: Boolean = true) = value

fun someObject() = mapOf("key1" to someNumber(), "key2" to someString())

private fun getBucketName(bucket: Bucket) = when (bucket) {
    is AliasedBucket -> bucket.alias
    is UnaliasedBucket -> bucket.name
}

fun <T : ValidType> someCaseClass(expression: TypeExpression<T>) = CaseClass(
    expression,
)

fun someStringSearchNumberResult(
    searchExpression: TypeExpression<StringType> = someString().toDopeType(),
    resultExpression: TypeExpression<NumberType> = someNumber().toDopeType(),
) = SearchResult(searchExpression, resultExpression)

fun someOrderExpression(typeExpression: TypeExpression<StringType> = someStringField(), orderByType: OrderType = ASC) = OrderExpression(
    typeExpression,
    orderByType,
)

fun someOrderingTerm(
    expression: TypeExpression<out ValidType> = someStringField(),
    orderType: OrderType? = null,
    nullsOrder: NullsOrder? = null,
) = OrderingTerm(expression, orderType, nullsOrder)

fun someWindowDefinition(
    windowReference: UnaliasedExpression<StringType>? = null,
    windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = WindowDefinition(
    windowReference = windowReference,
    windowPartitionClause = windowPartitionClause,
    windowOrderClause = windowOrderClause,
    windowFrameClause = windowFrameClause,
)

fun someWindowFrameClause(
    windowFrameType: WindowFrameType = RANGE,
    windowFrameExtent: WindowFrameExtent = someWindowFrameExtent(),
    windowFrameExclusion: WindowFrameExclusion? = null,
) = WindowFrameClause(windowFrameType, windowFrameExtent, windowFrameExclusion)

fun someWindowFrameExtent() = UnboundedPreceding()

fun someWindowFrameExclusion() = WindowFrameExclusion.EXCLUDE_GROUP
