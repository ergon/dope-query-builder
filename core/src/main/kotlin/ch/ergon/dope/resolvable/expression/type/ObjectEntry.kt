package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class ObjectEntry<T : ValidType>(val objectExpression: TypeExpression<ObjectType>, val key: String) : TypeExpression<T>

private fun <T : ValidType> TypeExpression<ObjectType>.get(key: String): ObjectEntry<T> = ObjectEntry(this, key)

fun TypeExpression<ObjectType>.getString(key: String): ObjectEntry<StringType> = get(key)

fun TypeExpression<ObjectType>.getNumber(key: String): ObjectEntry<NumberType> = get(key)

fun TypeExpression<ObjectType>.getBoolean(key: String): ObjectEntry<BooleanType> = get(key)

fun TypeExpression<ObjectType>.getObject(key: String): ObjectEntry<ObjectType> = get(key)

fun <T : ValidType> TypeExpression<ObjectType>.getArray(key: String): ObjectEntry<ArrayType<T>> = get(key)

fun TypeExpression<ObjectType>.getNumberArray(key: String): ObjectEntry<ArrayType<NumberType>> = get(key)

fun TypeExpression<ObjectType>.getStringArray(key: String): ObjectEntry<ArrayType<StringType>> = get(key)

fun TypeExpression<ObjectType>.getBooleanArray(key: String): ObjectEntry<ArrayType<BooleanType>> = get(key)

fun TypeExpression<ObjectType>.getObjectArray(key: String): ObjectEntry<ArrayType<ObjectType>> = get(key)

fun TypeExpression<ObjectType>.getAnyTypeArray(key: String): ObjectEntry<ArrayType<ValidType>> = get(key)
