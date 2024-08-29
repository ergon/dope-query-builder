package ch.ergon.dope.helper

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.CountAsteriskExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.TRUE
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.SearchResult
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.SimpleCaseClass
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.UnaliasedBucket
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

fun someBucket(name: String = "someBucket") = UnaliasedBucket(name)

fun someNumberField(name: String = "numberField", bucket: Bucket = someBucket("")) = Field<NumberType>(name, getBucketName(bucket))

fun someStringField(name: String = "stringField", bucket: Bucket = someBucket("")) = Field<StringType>(name, getBucketName(bucket))

fun someBooleanField(name: String = "booleanField", bucket: Bucket = someBucket("")) = Field<BooleanType>(name, getBucketName(bucket))

fun someBooleanExpression() = TRUE

fun someUnaliasedExpression() = CountAsteriskExpression()

fun someNumberArrayField(name: String = "numberArrayField", bucket: Bucket = someBucket("")) =
    Field<ArrayType<NumberType>>(name, getBucketName(bucket))

fun someStringArrayField(name: String = "stringArrayField", bucket: Bucket = someBucket("")) =
    Field<ArrayType<StringType>>(name, getBucketName(bucket))

fun someBooleanArrayField(name: String = "booleanArrayField", bucket: Bucket = someBucket("")) =
    Field<ArrayType<BooleanType>>(name, getBucketName(bucket))

fun someNumber(value: Number = 5) = value

fun someString(value: String = "someString") = value

fun someBoolean(value: Boolean = true) = value

private fun getBucketName(bucket: Bucket) = when (bucket) {
    is AliasedBucket -> bucket.alias
    is UnaliasedBucket -> bucket.name
}

fun <T : ValidType> someCaseClass(expression: TypeExpression<T>) = SimpleCaseClass(
    expression,
)

fun someStringSearchNumberResult(
    searchExpression: UnaliasedExpression<StringType> = someString().toDopeType(),
    resultExpression: UnaliasedExpression<NumberType> = someNumber().toDopeType(),
) = SearchResult(searchExpression, resultExpression)
