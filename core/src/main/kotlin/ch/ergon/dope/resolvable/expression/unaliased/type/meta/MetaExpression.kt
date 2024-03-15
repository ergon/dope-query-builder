package ch.ergon.dope.resolvable.expression.unaliased.type.meta

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class MetaExpression(private val bucket: Bucket) : TypeExpression<StringType> {
    override fun toQueryString(): String = "META(${
    when (bucket) {
        is AliasedBucket -> bucket.alias
        else -> bucket.toQueryString()
    }
    })"

    val cas: Field<NumberType> = Field("cas", toQueryString())

    val expiration: Field<NumberType> = Field("expiration", toQueryString())

    val flags: Field<NumberType> = Field("flags", toQueryString())

    val id: Field<StringType> = Field("id", toQueryString())

    val type: Field<StringType> = Field("type", toQueryString())

    val keyspace: Field<StringType> = Field("keyspace", toQueryString())
}

fun meta(bucket: Bucket) = MetaExpression(bucket)
