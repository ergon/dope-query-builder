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

open class Primitive<T : ValidType>() : TypeExpression<T> {
    private var queryString: String = ""

    constructor(type: PrimitiveType) : this() {
        this.queryString = when (type) {
            PrimitiveType.NULL -> "NULL"
            PrimitiveType.MISSING -> "MISSING"
            PrimitiveType.TRUE -> "TRUE"
            PrimitiveType.FALSE -> "FALSE"
        }
    }

    override fun toDopeQuery() = DopeQuery(
        queryString = queryString,
        parameters = emptyMap(),
    )

    companion object {
        val NULL = Primitive<NullType>(PrimitiveType.NULL)
        val MISSING = Primitive<MissingType>(PrimitiveType.MISSING)
        val TRUE = Primitive<BooleanType>(PrimitiveType.TRUE)
        val FALSE = Primitive<BooleanType>(PrimitiveType.FALSE)
    }
}

class NumberPrimitive(private val value: Number) : Primitive<NumberType>() {
    override fun toDopeQuery() = DopeQuery(
        queryString = "$value",
        parameters = emptyMap(),
    )
}

class StringPrimitive(private val value: String) : Primitive<StringType>() {
    override fun toDopeQuery() = DopeQuery(
        queryString = "\"$value\"",
        parameters = emptyMap(),
    )
}

class BooleanPrimitive(private val value: Boolean) : Primitive<BooleanType>() {
    override fun toDopeQuery() = DopeQuery(
        queryString = when (value) {
            true -> TRUE.toDopeQuery().queryString
            false -> FALSE.toDopeQuery().queryString
        },
        parameters = emptyMap(),
    )
}

class ArrayPrimitive<T : ValidType>(private val collection: Collection<TypeExpression<out T>>) : Primitive<ArrayType<T>>() {
    override fun toDopeQuery(): DopeQuery {
        val dopeQueries = collection.map { it.toDopeQuery() }
        return DopeQuery(
            queryString = dopeQueries.joinToString(separator = ", ", prefix = "[", postfix = "]") { it.queryString },
            parameters = dopeQueries.fold(emptyMap()) { parameters, dopeQueryElement ->
                parameters + dopeQueryElement.parameters
            },
        )
    }
}

fun String.toStringType(): StringPrimitive = StringPrimitive(this)

fun Number.toNumberType(): NumberPrimitive = NumberPrimitive(this)

fun Boolean.toBooleanType(): BooleanPrimitive = BooleanPrimitive(this)

fun <T : ValidType> Collection<TypeExpression<out T>>.toArrayType() = ArrayPrimitive(this)

enum class PrimitiveType {
    NULL,
    MISSING,
    TRUE,
    FALSE,
}
