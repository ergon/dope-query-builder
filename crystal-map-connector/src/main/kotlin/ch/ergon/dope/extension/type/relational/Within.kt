package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.NotWithinExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.WithinExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.notWithinArray
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.withinArray
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterList
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("withinNumberArray")
fun CMField<out Number>.withinArray(array: TypeExpression<ArrayType<NumberType>>): WithinExpression<NumberType> =
    toDopeType().withinArray(array)

@JvmName("withinStringArray")
fun CMField<String>.withinArray(array: TypeExpression<ArrayType<StringType>>): WithinExpression<StringType> =
    toDopeType().withinArray(array)

@JvmName("withinBooleanArray")
fun CMField<Boolean>.withinArray(array: TypeExpression<ArrayType<BooleanType>>): WithinExpression<BooleanType> =
    toDopeType().withinArray(array)

@JvmName("withinNumberArray")
fun TypeExpression<NumberType>.withinArray(array: CMList<out Number>): WithinExpression<NumberType> =
    withinArray(array.toDopeType())

@JvmName("withinStringArray")
fun TypeExpression<StringType>.withinArray(array: CMList<String>): WithinExpression<StringType> =
    withinArray(array.toDopeType())

@JvmName("withinBooleanArray")
fun TypeExpression<BooleanType>.withinArray(array: CMList<Boolean>): WithinExpression<BooleanType> =
    withinArray(array.toDopeType())

@JvmName("withinNumberArray")
fun CMField<out Number>.withinArray(array: CMList<out Number>): WithinExpression<NumberType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinStringArray")
fun CMField<String>.withinArray(array: CMList<String>): WithinExpression<StringType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinBooleanArray")
fun CMField<Boolean>.withinArray(array: CMList<Boolean>): WithinExpression<BooleanType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinNumberArray")
fun CMField<out Number>.withinArray(array: Collection<TypeExpression<NumberType>>): WithinExpression<NumberType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinStringArray")
fun CMField<String>.withinArray(array: Collection<TypeExpression<StringType>>): WithinExpression<StringType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinBooleanArray")
fun CMField<Boolean>.withinArray(array: Collection<TypeExpression<BooleanType>>): WithinExpression<BooleanType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinNumberArray")
fun Number.withinArray(array: CMList<out Number>): WithinExpression<NumberType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinStringArray")
fun String.withinArray(array: CMList<String>): WithinExpression<StringType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinBooleanArray")
fun Boolean.withinArray(array: CMList<Boolean>): WithinExpression<BooleanType> =
    toDopeType().withinArray(array.toDopeType())

@JvmName("withinArrayNumberConverter")
fun <KotlinType : Any, MapType : Number> KotlinType.withinArray(array: CMConverterList<KotlinType, MapType>): WithinExpression<NumberType> =
    toDopeType(array).withinArray(array.toDopeType())

@JvmName("withinArrayStringConverter")
fun <KotlinType : Any> KotlinType.withinArray(array: CMConverterList<KotlinType, String>): WithinExpression<StringType> =
    toDopeType(array).withinArray(array.toDopeType())

@JvmName("withinArrayBooleanConverter")
fun <KotlinType : Any> KotlinType.withinArray(array: CMConverterList<KotlinType, Boolean>): WithinExpression<BooleanType> =
    toDopeType(array).withinArray(array.toDopeType())

@JvmName("notWithinNumberArray")
fun CMField<out Number>.notWithinArray(array: TypeExpression<ArrayType<NumberType>>): NotWithinExpression<NumberType> =
    toDopeType().notWithinArray(array)

@JvmName("notWithinStringArray")
fun CMField<String>.notWithinArray(array: TypeExpression<ArrayType<StringType>>): NotWithinExpression<StringType> =
    toDopeType().notWithinArray(array)

@JvmName("notWithinBooleanArray")
fun CMField<Boolean>.notWithinArray(array: TypeExpression<ArrayType<BooleanType>>): NotWithinExpression<BooleanType> =
    toDopeType().notWithinArray(array)

@JvmName("notWithinNumberArray")
fun TypeExpression<NumberType>.notWithinArray(array: CMList<out Number>): NotWithinExpression<NumberType> =
    this.notWithinArray(array.toDopeType())

@JvmName("notWithinStringArray")
fun TypeExpression<StringType>.notWithinArray(array: CMList<String>): NotWithinExpression<StringType> =
    this.notWithinArray(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun TypeExpression<BooleanType>.notWithinArray(array: CMList<Boolean>): NotWithinExpression<BooleanType> =
    this.notWithinArray(array.toDopeType())

@JvmName("notWithinNumberArray")
fun CMField<out Number>.notWithinArray(array: CMList<out Number>): NotWithinExpression<NumberType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinStringArray")
fun CMField<String>.notWithinArray(array: CMList<String>): NotWithinExpression<StringType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun CMField<Boolean>.notWithinArray(array: CMList<Boolean>): NotWithinExpression<BooleanType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinNumberArray")
fun CMField<out Number>.notWithinArray(array: Collection<TypeExpression<NumberType>>): NotWithinExpression<NumberType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinStringArray")
fun CMField<String>.notWithinArray(array: Collection<TypeExpression<StringType>>): NotWithinExpression<StringType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun CMField<Boolean>.notWithinArray(array: Collection<TypeExpression<BooleanType>>): NotWithinExpression<BooleanType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinNumberArray")
fun Number.notWithinArray(array: CMList<out Number>): NotWithinExpression<NumberType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinStringArray")
fun String.notWithinArray(array: CMList<String>): NotWithinExpression<StringType> =
    toDopeType().notWithinArray(array.toDopeType())

@JvmName("notWithinBooleanArray")
fun Boolean.notWithinArray(array: CMList<Boolean>): NotWithinExpression<BooleanType> =
    toDopeType().notWithinArray(array.toDopeType())
