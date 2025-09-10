package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.MissingType
import ch.ergon.dope.validtype.NullType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

sealed class Primitive<T : ValidType> : TypeExpression<T>

data object NULL : Primitive<NullType>()
data object MISSING : Primitive<MissingType>()
data object TRUE : Primitive<BooleanType>()
data object FALSE : Primitive<BooleanType>()

data class NumberPrimitive(val value: Number) : Primitive<NumberType>()

data class StringPrimitive(val value: String) : Primitive<StringType>()

data class BooleanPrimitive(val value: Boolean) : Primitive<BooleanType>()

data class ArrayPrimitive<T : ValidType>(val collection: Collection<TypeExpression<out T>>) : Primitive<ArrayType<T>>()

data class ObjectPrimitive(
    val entries: List<ObjectEntryPrimitive<out ValidType>>,
) : Primitive<ObjectType>()

data class ObjectEntryPrimitive<T : ValidType>(
    val key: TypeExpression<StringType>,
    val value: TypeExpression<T>,
) : Resolvable

fun TypeExpression<StringType>.toObjectEntry(value: TypeExpression<out ValidType>) = ObjectEntryPrimitive(this, value)

fun String.toObjectEntry(value: TypeExpression<out ValidType>) = toDopeType().toObjectEntry(value)

fun List<ObjectEntryPrimitive<out ValidType>>.toDopeType(): ObjectPrimitive = ObjectPrimitive(this)

fun <V> Map<String, V>.toDopeType(): ObjectPrimitive =
    ObjectPrimitive(
        map { (key, value) ->
            when (value) {
                is TypeExpression<*> -> key.toDopeType().toObjectEntry(value)
                else -> key.toDopeType().toObjectEntry(value.toDopeType())
            }
        },
    )

fun String.toDopeType() = StringPrimitive(this)

fun Number.toDopeType() = NumberPrimitive(this)

fun Boolean.toDopeType() = BooleanPrimitive(this)

fun <T : ValidType> Collection<TypeExpression<out T>>.toDopeType() = ArrayPrimitive(this)

@JvmName("anyListToDopeType")
fun <T> Collection<T>.toDopeType(): ArrayPrimitive<ValidType> = map { it.toDopeType() }.toDopeType()

@JvmName("numberListToDopeType")
fun Collection<Number>.toDopeType(): ArrayPrimitive<NumberType> = map { it.toDopeType() }.toDopeType()

@JvmName("stringListToDopeType")
fun Collection<String>.toDopeType(): ArrayPrimitive<StringType> = map { it.toDopeType() }.toDopeType()

@JvmName("booleanListToDopeType")
fun Collection<Boolean>.toDopeType(): ArrayPrimitive<BooleanType> = map { it.toDopeType() }.toDopeType()

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
