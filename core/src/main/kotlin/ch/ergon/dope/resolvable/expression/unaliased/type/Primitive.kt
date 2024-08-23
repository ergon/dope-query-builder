package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.MissingType
import ch.ergon.dope.validtype.NullType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

sealed class Primitive<T : ValidType>(
    private val generateDopeQuery: (DopeQueryManager) -> DopeQuery,
) : TypeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager) = generateDopeQuery(manager)
}

data object NULL : Primitive<NullType>(
    { manager: DopeQueryManager -> DopeQuery("NULL", emptyMap(), manager) },
)
data object MISSING : Primitive<MissingType>(
    { manager: DopeQueryManager -> DopeQuery("MISSING", emptyMap(), manager) },
)
data object TRUE : Primitive<BooleanType>(
    { manager: DopeQueryManager -> DopeQuery("TRUE", emptyMap(), manager) },
)
data object FALSE : Primitive<BooleanType>(
    { manager: DopeQueryManager -> DopeQuery("FALSE", emptyMap(), manager) },
)

class NumberPrimitive(value: Number) : Primitive<NumberType>(
    {
            manager: DopeQueryManager ->
        DopeQuery(
            queryString = "$value",
            parameters = emptyMap(),
            manager = manager,
        )
    },
)

class StringPrimitive(value: String) : Primitive<StringType>(
    {
            manager: DopeQueryManager ->
        DopeQuery(
            queryString = "\"$value\"",
            parameters = emptyMap(),
            manager = manager,
        )
    },
)

class BooleanPrimitive(value: Boolean) : Primitive<BooleanType>(
    {
            manager: DopeQueryManager ->
        DopeQuery(
            queryString = when (value) {
                true -> TRUE.toDopeQuery(manager).queryString
                false -> FALSE.toDopeQuery(manager).queryString
            },
            parameters = emptyMap(),
            manager = manager,
        )
    },
)

class ArrayPrimitive<T : ValidType>(collection: Collection<TypeExpression<out T>>) : Primitive<ArrayType<T>>(
    {
            manager: DopeQueryManager ->
        collection.map { it.toDopeQuery(manager) }.let { dopeQueries ->
            DopeQuery(
                queryString = dopeQueries.joinToString(", ", prefix = "[", postfix = "]") { it.queryString },
                parameters = dopeQueries.fold(emptyMap()) { parameters, dopeQueryElement -> parameters + dopeQueryElement.parameters },
                manager = manager,
            )
        }
    },
)

fun String.toDopeType() = StringPrimitive(this)

fun Number.toDopeType() = NumberPrimitive(this)

fun Boolean.toDopeType() = BooleanPrimitive(this)

fun <T : ValidType> Collection<TypeExpression<out T>>.toDopeType() = ArrayPrimitive(this)
