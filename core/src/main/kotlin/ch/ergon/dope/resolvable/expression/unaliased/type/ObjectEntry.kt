package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ObjectEntry<T : ValidType>(private val objectExpression: TypeExpression<ObjectType>, private val key: String) : Field<T>(key, "") {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val objectExpressionDopeQuery = objectExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${objectExpressionDopeQuery.queryString}.`$key`",
            parameters = objectExpressionDopeQuery.parameters,
        )
    }
}

private fun <T : ValidType> TypeExpression<ObjectType>.get(key: String): ObjectEntry<T> = ObjectEntry(this, key)

fun TypeExpression<ObjectType>.getString(key: String): ObjectEntry<StringType> = get(key)

fun TypeExpression<ObjectType>.getNumber(key: String): ObjectEntry<NumberType> = get(key)

fun TypeExpression<ObjectType>.getBoolean(key: String): ObjectEntry<BooleanType> = get(key)

fun TypeExpression<ObjectType>.getObject(key: String): ObjectEntry<ObjectType> = get(key)

fun <T : ValidType> TypeExpression<ObjectType>.getArray(key: String): ObjectEntry<ArrayType<T>> = get(key)
