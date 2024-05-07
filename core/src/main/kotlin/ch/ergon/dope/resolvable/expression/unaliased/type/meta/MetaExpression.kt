package ch.ergon.dope.resolvable.expression.unaliased.type.meta

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class MetaExpression(private val bucket: Bucket) : TypeExpression<StringType> {
    override fun toQuery(): DopeQuery {
        val bucketDopeQuery = bucket.toQuery()
        return DopeQuery(
            queryString = "META(${
            when (bucket) {
                is AliasedBucket -> bucket.alias
                else -> bucketDopeQuery.queryString
            }
            })",
            parameters = bucketDopeQuery.parameters,
        )
    }

    val cas: Field<NumberType> = Field("cas", toQuery().queryString)

    val expiration: Field<NumberType> = Field("expiration", toQuery().queryString)

    val flags: Field<NumberType> = Field("flags", toQuery().queryString)

    val id: Field<StringType> = Field("id", toQuery().queryString)

    val type: Field<StringType> = Field("type", toQuery().queryString)

    val keyspace: Field<StringType> = Field("keyspace", toQuery().queryString)
}

fun meta(bucket: Bucket) = MetaExpression(bucket)
