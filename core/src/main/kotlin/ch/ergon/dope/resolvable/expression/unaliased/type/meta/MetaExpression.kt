package ch.ergon.dope.resolvable.expression.unaliased.type.meta

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.ObjectEntryField
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.UnaliasedBucket
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType

private const val META = "META"

class MetaExpression(private val bucket: Bucket?) : TypeExpression<ObjectType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager) =
        if (bucket == null) {
            DopeQuery(
                queryString = "$META()",
                parameters = emptyMap(),
            )
        } else {
            val bucketDopeQuery = bucket.toDopeQuery(manager)
            DopeQuery(
                queryString = toFunctionQueryString(
                    symbol = META,
                    when (bucket) {
                        is AliasedBucket -> "`${bucket.alias}`"
                        is UnaliasedBucket -> bucketDopeQuery.queryString
                    },
                ),
                parameters = bucketDopeQuery.parameters,
            )
        }

    val cas: Field<NumberType> = ObjectEntryField(this, "cas")

    val expiration: Field<NumberType> = ObjectEntryField(this, "expiration")

    val flags: Field<NumberType> = ObjectEntryField(this, "flags")

    val id: Field<StringType> = ObjectEntryField(this, "id")

    val type: Field<StringType> = ObjectEntryField(this, "type")

    val keyspace: Field<StringType> = ObjectEntryField(this, "keyspace")
}

fun meta(bucket: Bucket) = MetaExpression(bucket)

fun meta() = MetaExpression(null)
