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

class Primitive<T : ValidType> : TypeExpression<T> {
    private val dopeQuery: DopeQuery

    override fun toDopeQuery(): DopeQuery = dopeQuery

    constructor(value: Number) {
        this.dopeQuery = DopeQuery(queryString = "$value", parameters = emptyMap())
    }

    constructor(value: String) {
        this.dopeQuery = DopeQuery(queryString = "\"$value\"", parameters = emptyMap())
    }

    constructor(value: Boolean) {
        this.dopeQuery = DopeQuery(
            queryString = when (value) {
                true -> TRUE.toDopeQuery().queryString
                false -> FALSE.toDopeQuery().queryString
            },
            parameters = emptyMap(),
        )
    }

    constructor(collection: Collection<TypeExpression<out ValidType>>) {
        val dopeQueryCollection = collection.map { it.toDopeQuery() }
        this.dopeQuery = DopeQuery(
            queryString = dopeQueryCollection.joinToString(separator = ", ", prefix = "[", postfix = "]") { it.queryString },
            parameters = dopeQueryCollection.fold(emptyMap()) { parameters, field ->
                parameters + field.parameters
            },
        )
    }

    private constructor(primitiveType: PrimitiveType) {
        val queryString = when (primitiveType) {
            PrimitiveType.NULL -> "NULL"
            PrimitiveType.MISSING -> "MISSING"
            PrimitiveType.TRUE -> "TRUE"
            PrimitiveType.FALSE -> "FALSE"
        }

        this.dopeQuery = DopeQuery(queryString, emptyMap())
    }

    companion object {
        val NULL = Primitive<NullType>(PrimitiveType.NULL)
        val MISSING = Primitive<MissingType>(PrimitiveType.MISSING)
        val TRUE = Primitive<BooleanType>(PrimitiveType.TRUE)
        val FALSE = Primitive<BooleanType>(PrimitiveType.FALSE)
    }
}

fun String.toStringType(): Primitive<StringType> = Primitive(this)

fun Number.toNumberType(): Primitive<NumberType> = Primitive(this)

fun Boolean.toBooleanType(): Primitive<BooleanType> = Primitive(this)

fun <T : ValidType> Collection<TypeExpression<out T>>.toArrayType(): Primitive<ArrayType<T>> = Primitive(this)

private enum class PrimitiveType {
    NULL,
    MISSING,
    TRUE,
    FALSE,
}
