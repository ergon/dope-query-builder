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
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

sealed class Primitive<T : ValidType>(
    private val generateDopeQuery: (DopeQueryManager) -> DopeQuery,
) : TypeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager) = generateDopeQuery(manager)
}

data object NULL : Primitive<NullType>({ DopeQuery("NULL", emptyMap(), emptyList()) })
data object MISSING : Primitive<MissingType>({ DopeQuery("MISSING", emptyMap(), emptyList()) })
data object TRUE : Primitive<BooleanType>({ DopeQuery("TRUE", emptyMap(), emptyList()) })
data object FALSE : Primitive<BooleanType>({ DopeQuery("FALSE", emptyMap(), emptyList()) })

class NumberPrimitive(value: Number) : Primitive<NumberType>(
    {
        DopeQuery(
            queryString = "$value",
            parameters = emptyMap(),
            positionalParameters = emptyList(),
        )
    },
)

class StringPrimitive(value: String) : Primitive<StringType>(
    {
        DopeQuery(
            queryString = "\"$value\"",
            parameters = emptyMap(),
            positionalParameters = emptyList(),
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
            positionalParameters = emptyList(),
        )
    },
)

class ArrayPrimitive<T : ValidType>(collection: Collection<TypeExpression<out T>>) : Primitive<ArrayType<T>>(
    { manager: DopeQueryManager ->
        collection.map { it.toDopeQuery(manager) }.let { dopeQueries ->
            DopeQuery(
                queryString = formatListToQueryStringWithBrackets(dopeQueries, prefix = "[", postfix = "]"),
                parameters = dopeQueries.fold(emptyMap()) { parameters, dopeQueryElement -> parameters + dopeQueryElement.parameters },
                positionalParameters = dopeQueries.fold(emptyList()) { positionalParameters, dopeQueryElement ->
                    positionalParameters + dopeQueryElement.positionalParameters
                },
            )
        }
    },
)

fun String.toDopeType() = StringPrimitive(this)

fun Number.toDopeType() = NumberPrimitive(this)

fun Boolean.toDopeType() = BooleanPrimitive(this)

fun <T : ValidType> Collection<TypeExpression<out T>>.toDopeType() = ArrayPrimitive(this)
