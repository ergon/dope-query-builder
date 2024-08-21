package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.InExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.NotInExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.inArray
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.notInArray
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMConverterList
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("inArrayNumber")
fun CMJsonField<out Number>.inArray(array: TypeExpression<ArrayType<NumberType>>): InExpression<NumberType> =
    toDopeType().inArray(array)

@JvmName("inArrayString")
fun CMJsonField<String>.inArray(array: TypeExpression<ArrayType<StringType>>): InExpression<StringType> =
    toDopeType().inArray(array)

@JvmName("inArrayBoolean")
fun CMJsonField<Boolean>.inArray(array: TypeExpression<ArrayType<BooleanType>>): InExpression<BooleanType> =
    toDopeType().inArray(array)

@JvmName("inArrayNumber")
fun TypeExpression<NumberType>.inArray(array: CMJsonList<out Number>): InExpression<NumberType> =
    inArray(array.toDopeType())

@JvmName("inArrayString")
fun TypeExpression<StringType>.inArray(array: CMJsonList<String>): InExpression<StringType> =
    inArray(array.toDopeType())

@JvmName("inArrayBoolean")
fun TypeExpression<BooleanType>.inArray(array: CMJsonList<Boolean>): InExpression<BooleanType> =
    inArray(array.toDopeType())

@JvmName("inArrayNumber")
fun CMJsonField<out Number>.inArray(array: CMJsonList<out Number>): InExpression<NumberType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayString")
fun CMJsonField<String>.inArray(array: CMJsonList<String>): InExpression<StringType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayBoolean")
fun CMJsonField<Boolean>.inArray(array: CMJsonList<Boolean>): InExpression<BooleanType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayNumber")
fun CMJsonField<out Number>.inArray(array: Collection<TypeExpression<NumberType>>): InExpression<NumberType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayString")
fun CMJsonField<String>.inArray(array: Collection<TypeExpression<StringType>>): InExpression<StringType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayBoolean")
fun CMJsonField<Boolean>.inArray(array: Collection<TypeExpression<BooleanType>>): InExpression<BooleanType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayNumber")
fun Number.inArray(array: CMJsonList<out Number>): InExpression<NumberType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayString")
fun String.inArray(array: CMJsonList<String>): InExpression<StringType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayBoolean")
fun Boolean.inArray(array: CMJsonList<Boolean>): InExpression<BooleanType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayNumberConverter")
fun <Convertable : Any, JsonType : Number> Convertable.inArray(array: CMConverterList<Convertable, JsonType>): InExpression<NumberType> =
    toDopeType(array).inArray(array.toDopeType())

@JvmName("inArrayStringConverter")
fun <Convertable : Any> Convertable.inArray(array: CMConverterList<Convertable, String>): InExpression<StringType> =
    toDopeType(array).inArray(array.toDopeType())

@JvmName("inArrayBooleanConverter")
fun <Convertable : Any> Convertable.inArray(array: CMConverterList<Convertable, Boolean>): InExpression<BooleanType> =
    toDopeType(array).inArray(array.toDopeType())

@JvmName("inArrayNumberConverter")
fun <Convertable : Any, JsonType : Number> CMConverterField<Convertable, JsonType>.inArray(array: Collection<Convertable>): InExpression<NumberType> =
    toDopeType().inArray(array.map { it.toDopeType(this) }.toDopeType())

@JvmName("inArrayStringConverter")
fun <Convertable : Any> CMConverterField<Convertable, String>.inArray(array: Collection<Convertable>): InExpression<StringType> =
    toDopeType().inArray(array.map { it.toDopeType(this) }.toDopeType())

@JvmName("inArrayBooleanConverter")
fun <Convertable : Any> CMConverterField<Convertable, Boolean>.inArray(array: Collection<Convertable>): InExpression<BooleanType> =
    toDopeType().inArray(array.map { it.toDopeType(this) }.toDopeType())

@JvmName("notInArrayNumber")
fun CMJsonField<out Number>.notInArray(array: TypeExpression<ArrayType<NumberType>>): NotInExpression<NumberType> =
    toDopeType().notInArray(array)

@JvmName("notInArrayString")
fun CMJsonField<String>.notInArray(array: TypeExpression<ArrayType<StringType>>): NotInExpression<StringType> =
    toDopeType().notInArray(array)

@JvmName("notInArrayBoolean")
fun CMJsonField<Boolean>.notInArray(array: TypeExpression<ArrayType<BooleanType>>): NotInExpression<BooleanType> =
    toDopeType().notInArray(array)

@JvmName("notInArrayNumber")
fun TypeExpression<NumberType>.notInArray(array: CMJsonList<out Number>): NotInExpression<NumberType> =
    this.notInArray(array.toDopeType())

@JvmName("notInArrayString")
fun TypeExpression<StringType>.notInArray(array: CMJsonList<String>): NotInExpression<StringType> =
    this.notInArray(array.toDopeType())

@JvmName("notInArrayBoolean")
fun TypeExpression<BooleanType>.notInArray(array: CMJsonList<Boolean>): NotInExpression<BooleanType> =
    this.notInArray(array.toDopeType())

@JvmName("notInArrayNumber")
fun CMJsonField<out Number>.notInArray(array: CMJsonList<out Number>): NotInExpression<NumberType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayString")
fun CMJsonField<String>.notInArray(array: CMJsonList<String>): NotInExpression<StringType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayBoolean")
fun CMJsonField<Boolean>.notInArray(array: CMJsonList<Boolean>): NotInExpression<BooleanType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayNumber")
fun CMJsonField<out Number>.notInArray(array: Collection<TypeExpression<NumberType>>): NotInExpression<NumberType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayString")
fun CMJsonField<String>.notInArray(array: Collection<TypeExpression<StringType>>): NotInExpression<StringType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayBoolean")
fun CMJsonField<Boolean>.notInArray(array: Collection<TypeExpression<BooleanType>>): NotInExpression<BooleanType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayNumber")
fun Number.notInArray(array: CMJsonList<out Number>): NotInExpression<NumberType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayString")
fun String.notInArray(array: CMJsonList<String>): NotInExpression<StringType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayBoolean")
fun Boolean.notInArray(array: CMJsonList<Boolean>): NotInExpression<BooleanType> =
    toDopeType().notInArray(array.toDopeType())
