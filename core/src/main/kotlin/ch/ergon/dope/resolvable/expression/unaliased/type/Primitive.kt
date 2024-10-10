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
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

sealed class Primitive<T : ValidType>(
    private val generateDopeQuery: (DopeQueryManager) -> DopeQuery,
) : TypeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager) = generateDopeQuery(manager)
}

data object NULL : Primitive<NullType>({ DopeQuery("NULL") })
data object MISSING : Primitive<MissingType>({ DopeQuery("MISSING") })
data object TRUE : Primitive<BooleanType>({ DopeQuery("TRUE") })
data object FALSE : Primitive<BooleanType>({ DopeQuery("FALSE") })

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

fun String.toDopeType() = StringPrimitive(this)

fun Number.toDopeType() = NumberPrimitive(this)

fun Boolean.toDopeType() = BooleanPrimitive(this)

fun <T : ValidType> Collection<TypeExpression<out T>>.toDopeType() = ArrayPrimitive(this)
