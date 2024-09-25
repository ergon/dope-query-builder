package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
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

data object NULL : Primitive<NullType>({ DopeQuery("NULL", emptyMap()) })
data object MISSING : Primitive<MissingType>({ DopeQuery("MISSING", emptyMap()) })
data object TRUE : Primitive<BooleanType>({ DopeQuery("TRUE", emptyMap()) })
data object FALSE : Primitive<BooleanType>({ DopeQuery("FALSE", emptyMap()) })

class NumberPrimitive(value: Number) : Primitive<NumberType>(
    {
        DopeQuery(
            queryString = "$value",
            parameters = emptyMap(),
        )
    },
)

class StringPrimitive(value: String) : Primitive<StringType>(
    {
        DopeQuery(
            queryString = "\"$value\"",
            parameters = emptyMap(),
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
            parameters = emptyMap(),
        )
    },
)

class ArrayPrimitive<T : ValidType>(collection: Collection<TypeExpression<out T>>) : Primitive<ArrayType<T>>(
    { manager: DopeQueryManager ->
        collection.map { it.toDopeQuery(manager) }.let { dopeQueries ->
            DopeQuery(
                queryString = formatListToQueryStringWithBrackets(dopeQueries, prefix = "[", postfix = "]"),
                parameters = dopeQueries.fold(emptyMap()) { parameters, dopeQueryElement -> parameters + dopeQueryElement.parameters },
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
            parameters = entryDopeQueries.fold(emptyMap()) { parameters, it -> parameters + it.parameters },
        )
    },
)

fun <K, V> Map<K, V>.toDopeType(): ObjectPrimitive =
    ObjectPrimitive(
        *map { (key, value) ->
            require(key is String) { "Key '$key' is not a String." }
            key.toDopeType().to(value.toDopeType())
        }.toTypedArray(),
    )

fun String.toDopeType() = StringPrimitive(this)

fun Number.toDopeType() = NumberPrimitive(this)

fun Boolean.toDopeType() = BooleanPrimitive(this)

fun <T : ValidType> Collection<TypeExpression<out T>>.toDopeType() = ArrayPrimitive(this)

@JvmName("anyListToDopeType")
fun <T> Collection<T>.toDopeType(): ArrayPrimitive<ValidType> = map { it.toDopeType() }.toDopeType()

fun <T> T.toDopeType() = when (this) {
    is Number -> this.toDopeType()
    is String -> this.toDopeType()
    is Boolean -> this.toDopeType()
    is Map<*, *> -> this.toDopeType()
    is Collection<*> -> this.toDopeType()
    else -> throw IllegalArgumentException("Type for value '$this' is not supported.")
}
