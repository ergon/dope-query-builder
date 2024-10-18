package ch.ergon.dope.resolvable.expression.unaliased.type.meta

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

private const val META = "META"

class MetaExpression(private val bucket: Bucket?) : TypeExpression<StringType>, FunctionOperator {
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

    val cas: Field<NumberType> = getMetaField("cas")

    val expiration: Field<NumberType> = getMetaField("expiration")

    val flags: Field<NumberType> = getMetaField("flags")

    val id: Field<StringType> = getMetaField("id")

    val type: Field<StringType> = getMetaField("type")

    val keyspace: Field<StringType> = getMetaField("keyspace")

    private fun <T : ValidType> getMetaField(field: String): MetaField<T> =
        MetaField(field) { manager: DopeQueryManager -> toDopeQuery(manager) }

    private class MetaField<T : ValidType>(
        private val name: String,
        private val generateDopeQuery: (DopeQueryManager) -> DopeQuery,
    ) : Field<T>(name, "") {
        override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
            val dopeQuery = generateDopeQuery(manager)
            return DopeQuery(
                queryString = "${dopeQuery.queryString}.`$name`",
                parameters = dopeQuery.parameters,
            )
        }
    }
}

fun meta(bucket: Bucket) = MetaExpression(bucket)

fun meta() = MetaExpression(null)
