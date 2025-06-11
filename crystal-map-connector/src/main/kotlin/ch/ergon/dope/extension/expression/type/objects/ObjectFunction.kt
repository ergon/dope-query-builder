package ch.ergon.dope.extension.expression.type.objects

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.addAttribute
import ch.ergon.dope.resolvable.expression.type.function.objects.concat
import ch.ergon.dope.resolvable.expression.type.function.objects.getInnerPairs
import ch.ergon.dope.resolvable.expression.type.function.objects.getLength
import ch.ergon.dope.resolvable.expression.type.function.objects.getNames
import ch.ergon.dope.resolvable.expression.type.function.objects.getNestedPairs
import ch.ergon.dope.resolvable.expression.type.function.objects.getPairs
import ch.ergon.dope.resolvable.expression.type.function.objects.getPaths
import ch.ergon.dope.resolvable.expression.type.function.objects.getValues
import ch.ergon.dope.resolvable.expression.type.function.objects.putAttribute
import ch.ergon.dope.resolvable.expression.type.function.objects.removeAttribute
import ch.ergon.dope.resolvable.expression.type.function.objects.renameAttribute
import ch.ergon.dope.resolvable.expression.type.function.objects.replace
import ch.ergon.dope.resolvable.expression.type.function.objects.unwrap
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMType
import com.schwarz.crystalapi.schema.Schema

fun CMObjectField<Schema>.addAttribute(key: TypeExpression<StringType>, value: TypeExpression<out ValidType>) =
    toDopeType().addAttribute(key, value)

fun CMObjectField<Schema>.addAttribute(key: String, value: TypeExpression<out ValidType>) =
    toDopeType().addAttribute(key.toDopeType(), value)

fun CMObjectField<Schema>.addAttribute(key: TypeExpression<StringType>, value: CMType) = toDopeType().addAttribute(key, value.toDopeType())

fun CMObjectField<Schema>.addAttribute(key: String, value: CMType) = toDopeType().addAttribute(key.toDopeType(), value.toDopeType())

fun TypeExpression<ObjectType>.addAttribute(key: TypeExpression<StringType>, value: CMType) = addAttribute(key, value.toDopeType())

fun CMObjectField<Schema>.addAttribute(key: CMJsonField<String>, value: TypeExpression<out ValidType>) =
    toDopeType().addAttribute(key.toDopeType(), value)

fun CMObjectField<Schema>.addAttribute(key: CMJsonField<String>, value: CMType) = toDopeType().addAttribute(key.toDopeType(), value.toDopeType())

fun TypeExpression<ObjectType>.addAttribute(key: CMJsonField<String>, value: CMType) = addAttribute(key.toDopeType(), value.toDopeType())

fun CMObjectField<Schema>.concat(
    secondObjectExpression: CMObjectField<Schema>,
    vararg additionalObjectExpression: CMObjectField<Schema>,
) = toDopeType().concat(secondObjectExpression.toDopeType(), *additionalObjectExpression.map { it.toDopeType() }.toTypedArray())

fun CMObjectField<Schema>.innerPairs() = toDopeType().getInnerPairs()

fun CMObjectField<Schema>.length() = toDopeType().getLength()

fun CMObjectField<Schema>.names() = toDopeType().getNames()

fun CMObjectField<Schema>.pairs() = toDopeType().getPairs()

fun CMObjectField<Schema>.pairsNested(options: TypeExpression<ObjectType>? = null) = toDopeType().getNestedPairs(options)

fun CMObjectField<Schema>.pairsNested(options: CMObjectField<Schema>) = toDopeType().getNestedPairs(options.toDopeType())

fun TypeExpression<ObjectType>.pairsNested(options: CMObjectField<Schema>) = getNestedPairs(options.toDopeType())

fun CMObjectField<Schema>.paths(options: TypeExpression<ObjectType>? = null) = toDopeType().getPaths(options)

fun CMObjectField<Schema>.paths(options: CMObjectField<Schema>) = toDopeType().getPaths(options.toDopeType())

fun TypeExpression<ObjectType>.paths(options: CMObjectField<Schema>) = getPaths(options.toDopeType())

fun CMObjectField<Schema>.putAttribute(key: TypeExpression<StringType>, value: TypeExpression<out ValidType>) =
    toDopeType().putAttribute(key, value)

fun CMObjectField<Schema>.putAttribute(key: TypeExpression<StringType>, value: CMType) = toDopeType().putAttribute(key, value.toDopeType())

fun CMObjectField<Schema>.putAttribute(key: String, value: TypeExpression<out ValidType>) = toDopeType().putAttribute(key.toDopeType(), value)

fun CMObjectField<Schema>.putAttribute(key: String, value: CMType) = toDopeType().putAttribute(key.toDopeType(), value.toDopeType())

fun CMObjectField<Schema>.putAttribute(key: CMJsonField<String>, value: TypeExpression<out ValidType>) =
    toDopeType().putAttribute(key.toDopeType(), value)

fun CMObjectField<Schema>.putAttribute(key: CMJsonField<String>, value: CMType) = toDopeType().putAttribute(key.toDopeType(), value.toDopeType())

fun TypeExpression<ObjectType>.putAttribute(key: String, value: CMType) = putAttribute(key.toDopeType(), value.toDopeType())

fun TypeExpression<ObjectType>.putAttribute(key: TypeExpression<StringType>, value: CMType) = putAttribute(key, value.toDopeType())

fun TypeExpression<ObjectType>.putAttribute(key: CMJsonField<String>, value: CMType) = putAttribute(key.toDopeType(), value.toDopeType())

fun TypeExpression<ObjectType>.putAttribute(key: CMJsonField<String>, value: TypeExpression<out ValidType>) =
    putAttribute(key.toDopeType(), value)

fun CMObjectField<Schema>.removeAttribute(key: TypeExpression<StringType>) = toDopeType().removeAttribute(key)

fun CMObjectField<Schema>.removeAttribute(key: String) = toDopeType().removeAttribute(key.toDopeType())

fun CMObjectField<Schema>.removeAttribute(key: CMJsonField<String>) = toDopeType().removeAttribute(key.toDopeType())

fun CMObjectField<Schema>.renameAttribute(
    oldFieldName: TypeExpression<StringType>,
    newFieldName: TypeExpression<StringType>,
) = toDopeType().renameAttribute(oldFieldName, newFieldName)

fun CMObjectField<Schema>.renameAttribute(
    oldFieldName: String,
    newFieldName: String,
) = toDopeType().renameAttribute(oldFieldName.toDopeType(), newFieldName.toDopeType())

fun CMObjectField<Schema>.renameAttribute(
    oldFieldName: TypeExpression<StringType>,
    newFieldName: String,
) = toDopeType().renameAttribute(oldFieldName, newFieldName.toDopeType())

fun CMObjectField<Schema>.renameAttribute(
    oldFieldName: String,
    newFieldName: TypeExpression<StringType>,
) = toDopeType().renameAttribute(oldFieldName.toDopeType(), newFieldName)

fun CMObjectField<Schema>.renameAttribute(
    oldFieldName: CMJsonField<String>,
    newFieldName: CMJsonField<String>,
) = toDopeType().renameAttribute(oldFieldName.toDopeType(), newFieldName.toDopeType())

fun CMObjectField<Schema>.renameAttribute(
    oldFieldName: CMJsonField<String>,
    newFieldName: String,
) = toDopeType().renameAttribute(oldFieldName.toDopeType(), newFieldName.toDopeType())

fun CMObjectField<Schema>.renameAttribute(
    oldFieldName: String,
    newFieldName: CMJsonField<String>,
) = toDopeType().renameAttribute(oldFieldName.toDopeType(), newFieldName.toDopeType())

fun CMObjectField<Schema>.renameAttribute(
    oldFieldName: CMJsonField<String>,
    newFieldName: TypeExpression<StringType>,
) = toDopeType().renameAttribute(oldFieldName.toDopeType(), newFieldName)

fun CMObjectField<Schema>.renameAttribute(
    oldFieldName: TypeExpression<StringType>,
    newFieldName: CMJsonField<String>,
) = toDopeType().renameAttribute(oldFieldName, newFieldName.toDopeType())

fun TypeExpression<ObjectType>.renameAttribute(
    oldFieldName: CMJsonField<String>,
    newFieldName: CMJsonField<String>,
) = renameAttribute(oldFieldName.toDopeType(), newFieldName.toDopeType())

fun TypeExpression<ObjectType>.renameAttribute(
    oldFieldName: CMJsonField<String>,
    newFieldName: String,
) = renameAttribute(oldFieldName.toDopeType(), newFieldName.toDopeType())

fun TypeExpression<ObjectType>.renameAttribute(
    oldFieldName: String,
    newFieldName: CMJsonField<String>,
) = renameAttribute(oldFieldName.toDopeType(), newFieldName.toDopeType())

fun TypeExpression<ObjectType>.renameAttribute(
    oldFieldName: CMJsonField<String>,
    newFieldName: TypeExpression<StringType>,
) = renameAttribute(oldFieldName.toDopeType(), newFieldName)

fun TypeExpression<ObjectType>.renameAttribute(
    oldFieldName: TypeExpression<StringType>,
    newFieldName: CMJsonField<String>,
) = renameAttribute(oldFieldName, newFieldName.toDopeType())

fun CMObjectField<Schema>.replace(oldValue: TypeExpression<out ValidType>, newValue: TypeExpression<out ValidType>) =
    toDopeType().replace(oldValue, newValue)

fun TypeExpression<ObjectType>.replace(oldValue: CMType, newValue: CMType) = replace(oldValue.toDopeType(), newValue.toDopeType())

fun TypeExpression<ObjectType>.replace(oldValue: TypeExpression<out ValidType>, newValue: CMType) = replace(oldValue, newValue.toDopeType())

fun TypeExpression<ObjectType>.replace(oldValue: CMType, newValue: TypeExpression<out ValidType>) = replace(oldValue.toDopeType(), newValue)

fun CMObjectField<Schema>.replace(oldValue: CMType, newValue: CMType) = toDopeType().replace(oldValue.toDopeType(), newValue.toDopeType())

fun CMObjectField<Schema>.replace(oldValue: TypeExpression<out ValidType>, newValue: CMType) =
    toDopeType().replace(oldValue, newValue.toDopeType())

fun CMObjectField<Schema>.replace(oldValue: CMType, newValue: TypeExpression<out ValidType>) =
    toDopeType().replace(oldValue.toDopeType(), newValue)

fun CMObjectField<Schema>.unwrap() = toDopeType().unwrap()

fun CMObjectField<Schema>.values() = toDopeType().getValues()
