package ch.ergon.dope.resolvable.expression.unaliased.type.meta

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.ObjectEntry
import ch.ergon.dope.resolvable.fromable.Bucket
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
            )
        } else {
            val bucketDopeQuery = bucket.toDopeQuery(manager)
            DopeQuery(
                queryString = toFunctionQueryString(
                    symbol = META,
                    bucketDopeQuery.queryString,
                ),
                parameters = bucketDopeQuery.parameters,
            )
        }

    val cas: Field<NumberType> = ObjectEntry(this, "cas")

    val expiration: Field<NumberType> = ObjectEntry(this, "expiration")

    val flags: Field<NumberType> = ObjectEntry(this, "flags")

    val id: Field<StringType> = ObjectEntry(this, "id")

    val type: Field<StringType> = ObjectEntry(this, "type")

    val keyspace: Field<StringType> = ObjectEntry(this, "keyspace")
}

fun meta(bucket: Bucket) = MetaExpression(bucket)

fun meta() = MetaExpression(null)
