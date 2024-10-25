package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ObjectEntry<T : ValidType>(
    private val key: TypeExpression<StringType>,
    private val value: TypeExpression<T>,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val keyQuery = key.toDopeQuery(manager)
        val valueQuery = value.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${keyQuery.queryString} : ${valueQuery.queryString}",
            parameters = keyQuery.parameters.merge(valueQuery.parameters),
        )
    }
}

fun TypeExpression<StringType>.toObjectEntry(value: TypeExpression<out ValidType>) = ObjectEntry(this, value)

class ObjectEntryField<T : ValidType>(private val objectType: TypeExpression<ObjectType>, private val key: String) : Field<T>(key, "") {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val objectTypeDopeQuery = objectType.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${objectTypeDopeQuery.queryString}.`$key`",
            parameters = objectTypeDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> TypeExpression<ObjectType>.get(key: String): ObjectEntryField<T> = ObjectEntryField(this, key)
