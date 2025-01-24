package ch.ergon.dope.extension.type.objectfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.addAttribute
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.innerPairs
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.length
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.names
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.objectField
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.pairs
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.pairsNested
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.paths
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.putAttribute
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.removeAttribute
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.renameAttribute
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.replace
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.unwrap
import ch.ergon.dope.resolvable.expression.unaliased.type.function.`object`.values
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
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

fun CMObjectField<Schema>.objectField(key: TypeExpression<StringType>) = toDopeType().objectField(key)

fun CMObjectField<Schema>.objectField(key: String) = toDopeType().objectField(key.toDopeType())

fun CMObjectField<Schema>.objectField(key: CMJsonField<String>) = toDopeType().objectField(key.toDopeType())

fun CMObjectField<Schema>.innerPairs() = toDopeType().innerPairs()

fun CMObjectField<Schema>.length() = toDopeType().length()

fun CMObjectField<Schema>.names() = toDopeType().names()

fun CMObjectField<Schema>.pairs() = toDopeType().pairs()

fun CMObjectField<Schema>.pairsNested(options: TypeExpression<ObjectType>? = null) = toDopeType().pairsNested(options)

fun CMObjectField<Schema>.pairsNested(options: CMObjectField<Schema>) = toDopeType().pairsNested(options.toDopeType())

fun TypeExpression<ObjectType>.pairsNested(options: CMObjectField<Schema>) = pairsNested(options.toDopeType())

fun CMObjectField<Schema>.paths(options: TypeExpression<ObjectType>? = null) = toDopeType().paths(options)

fun CMObjectField<Schema>.paths(options: CMObjectField<Schema>) = toDopeType().paths(options.toDopeType())

fun TypeExpression<ObjectType>.paths(options: CMObjectField<Schema>) = paths(options.toDopeType())

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

fun CMObjectField<Schema>.values() = toDopeType().values()
