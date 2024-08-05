package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.MissingType
import ch.ergon.dope.validtype.NullType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

sealed class Primitive<T : ValidType>(
    private val dopeQuery: () -> DopeQuery,
) : TypeExpression<T> {
    override fun toDopeQuery() = dopeQuery()
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
    {
        DopeQuery(
            queryString = when (value) {
                true -> TRUE.toDopeQuery().queryString
                false -> FALSE.toDopeQuery().queryString
            },
            parameters = emptyMap(),
        )
    },
)

class ArrayPrimitive<T : ValidType>(collection: Collection<TypeExpression<out T>>) : Primitive<ArrayType<T>>(
    {
        collection.map { it.toDopeQuery() }.let { dopeQueries ->
            DopeQuery(
                queryString = dopeQueries.joinToString(", ", prefix = "[", postfix = "]") { it.queryString },
                parameters = dopeQueries.fold(emptyMap()) { parameters, dopeQueryElement -> parameters + dopeQueryElement.parameters },
            )
        }
    },
)

fun String.toDopeType() = StringPrimitive(this)

fun Number.toDopeType() = NumberPrimitive(this)

fun Boolean.toDopeType() = BooleanPrimitive(this)

fun <T : ValidType> Collection<TypeExpression<out T>>.toDopeType() = ArrayPrimitive(this)
