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
    private val queryString: String
    private val parameters = mutableMapOf<String, Any>()

    override fun toDopeQuery(): DopeQuery {
        return DopeQuery(queryString = queryString, parameters = parameters)
    }

    constructor(value: Number) {
        this.queryString = "$value"
    }

    constructor(value: String) {
        this.queryString = "\"$value\""
    }

    constructor(value: Boolean) {
        this.queryString = when (value) {
            true -> TRUE.queryString
            false -> FALSE.queryString
        }
    }

    constructor(collection: Collection<TypeExpression<out ValidType>>) {
        this.queryString = collection.joinToString(separator = ", ", prefix = "[", postfix = "]") {
            val dopeQuery = it.toDopeQuery()
            parameters.putAll(dopeQuery.parameters)
            dopeQuery.queryString
        }
    }

    private constructor(primitiveType: PrimitiveType) {
        this.queryString = when (primitiveType) {
            PrimitiveType.NULL -> "NULL"
            PrimitiveType.MISSING -> "MISSING"
            PrimitiveType.TRUE -> "TRUE"
            PrimitiveType.FALSE -> "FALSE"
        }
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
