package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.UnaliasedBucket
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

fun someBucket(name: String = "someBucket") = UnaliasedBucket(name)

fun someNumberField(name: String = "numField", bucket: Bucket = UnaliasedBucket("")) = Field<NumberType>(name, getBucketName(bucket))

fun someStringField(name: String = "strField", bucket: Bucket = UnaliasedBucket("")) = Field<StringType>(name, getBucketName(bucket))

fun someBooleanField(name: String = "boolField", bucket: Bucket = UnaliasedBucket("")) = Field<BooleanType>(name, getBucketName(bucket))

private fun getBucketName(bucket: Bucket): String = when (bucket) {
    is AliasedBucket -> bucket.alias
    else -> bucket.name
}
