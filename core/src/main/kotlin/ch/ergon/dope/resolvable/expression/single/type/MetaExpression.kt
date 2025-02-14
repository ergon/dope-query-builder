package ch.ergon.dope.resolvable.expression.single.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Bucket
import ch.ergon.dope.util.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

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

    val cas: Field<NumberType> = MetaField(this, "cas")

    val expiration: Field<NumberType> = MetaField(this, "expiration")

    val flags: Field<NumberType> = MetaField(this, "flags")

    val id: Field<StringType> = MetaField(this, "id")

    val type: Field<StringType> = MetaField(this, "type")

    val keyspace: Field<StringType> = MetaField(this, "keyspace")

    private class MetaField<T : ValidType>(
        private val metaExpression: MetaExpression,
        private val name: String,
    ) : Field<T>(name, "") {
        override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
            val metaExpressionDopeQuery = metaExpression.toDopeQuery(manager)
            return DopeQuery(
                queryString = "${metaExpressionDopeQuery.queryString}.`$name`",
                parameters = metaExpressionDopeQuery.parameters,
            )
        }
    }
}

fun meta(bucket: Bucket) = MetaExpression(bucket)

fun meta() = MetaExpression(null)
