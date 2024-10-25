package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.merge
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatListToQueryStringWithBrackets
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.MissingType
import ch.ergon.dope.validtype.NullType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

sealed class Primitive<T : ValidType>(
    private val generateDopeQuery: (DopeQueryManager) -> DopeQuery,
) : TypeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager) = generateDopeQuery(manager)
}

data object NULL : Primitive<NullType>({ DopeQuery(queryString = "NULL") })
data object MISSING : Primitive<MissingType>({ DopeQuery(queryString = "MISSING") })
data object TRUE : Primitive<BooleanType>({ DopeQuery(queryString = "TRUE") })
data object FALSE : Primitive<BooleanType>({ DopeQuery(queryString = "FALSE") })

class NumberPrimitive(value: Number) : Primitive<NumberType>(
    {
        DopeQuery(
            queryString = "$value",
        )
    },
)

class StringPrimitive(value: String) : Primitive<StringType>(
    {
        DopeQuery(
            queryString = "\"$value\"",
        )
    },
)

class BooleanPrimitive(value: Boolean) : Primitive<BooleanType>(
    { manager: DopeQueryManager ->
        DopeQuery(
            queryString = when (value) {
                true -> TRUE.toDopeQuery(manager).queryString
                false -> FALSE.toDopeQuery(manager).queryString
            },
        )
    },
)

class ArrayPrimitive<T : ValidType>(collection: Collection<TypeExpression<out T>>) : Primitive<ArrayType<T>>(
    { manager: DopeQueryManager ->
        collection.map { it.toDopeQuery(manager) }.let { dopeQueries ->
            DopeQuery(
                queryString = formatListToQueryStringWithBrackets(dopeQueries, prefix = "[", postfix = "]"),
                parameters = dopeQueries.map { it.parameters }.merge(),
            )
        }
    },
)

class ObjectPrimitive(
    private vararg val entries: ObjectEntry<out ValidType>,
) : Primitive<ObjectType>(
    { manager: DopeQueryManager ->
        val entryDopeQueries = entries.map { it.toDopeQuery(manager) }
        DopeQuery(
            queryString = "{${entryDopeQueries.joinToString(", ") { it.queryString }}}",
            parameters = entryDopeQueries.map { it.parameters }.merge(),
        )
    },
)

fun <V> Map<String, V>.toDopeType(): ObjectPrimitive =
    ObjectPrimitive(
        *map { (key, value) ->
            key.toDopeType().toObjectEntry(value.toDopeType())
        }.toTypedArray(),
    )

fun String.toDopeType() = StringPrimitive(this)

fun Number.toDopeType() = NumberPrimitive(this)

fun Boolean.toDopeType() = BooleanPrimitive(this)

fun <T : ValidType> Collection<TypeExpression<out T>>.toDopeType() = ArrayPrimitive(this)

@JvmName("anyListToDopeType")
fun <T> Collection<T>.toDopeType(): ArrayPrimitive<ValidType> = map { it.toDopeType() }.toDopeType()

@Suppress("UNCHECKED_CAST")
private fun <T> T.toDopeType() = when (this) {
    is Number -> this.toDopeType()
    is String -> this.toDopeType()
    is Boolean -> this.toDopeType()
    is Map<*, *> -> (this as? Map<String, Any>)?.toDopeType() ?: throw wrongTypeException()
    is Collection<*> -> this.toDopeType()
    else -> throw wrongTypeException()
}

private fun <T> T.wrongTypeException(): IllegalArgumentException {
    return IllegalArgumentException("Type for value '$this' is not supported.")
}
