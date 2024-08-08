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
import com.schwarz.crystalapi.schema.CMConverterList
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("inArrayNumber")
fun CMField<out Number>.inArray(array: TypeExpression<ArrayType<NumberType>>): InExpression<NumberType> =
    toDopeType().inArray(array)

@JvmName("inArrayString")
fun CMField<String>.inArray(array: TypeExpression<ArrayType<StringType>>): InExpression<StringType> =
    toDopeType().inArray(array)

@JvmName("inArrayBoolean")
fun CMField<Boolean>.inArray(array: TypeExpression<ArrayType<BooleanType>>): InExpression<BooleanType> =
    toDopeType().inArray(array)

@JvmName("inArrayNumber")
fun TypeExpression<NumberType>.inArray(array: CMList<out Number>): InExpression<NumberType> =
    inArray(array.toDopeType())

@JvmName("inArrayString")
fun TypeExpression<StringType>.inArray(array: CMList<String>): InExpression<StringType> =
    inArray(array.toDopeType())

@JvmName("inArrayBoolean")
fun TypeExpression<BooleanType>.inArray(array: CMList<Boolean>): InExpression<BooleanType> =
    inArray(array.toDopeType())

@JvmName("inArrayNumber")
fun CMField<out Number>.inArray(array: CMList<out Number>): InExpression<NumberType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayString")
fun CMField<String>.inArray(array: CMList<String>): InExpression<StringType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayBoolean")
fun CMField<Boolean>.inArray(array: CMList<Boolean>): InExpression<BooleanType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayNumber")
fun CMField<out Number>.inArray(array: Collection<TypeExpression<NumberType>>): InExpression<NumberType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayString")
fun CMField<String>.inArray(array: Collection<TypeExpression<StringType>>): InExpression<StringType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayBoolean")
fun CMField<Boolean>.inArray(array: Collection<TypeExpression<BooleanType>>): InExpression<BooleanType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayNumber")
fun Number.inArray(array: CMList<out Number>): InExpression<NumberType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayString")
fun String.inArray(array: CMList<String>): InExpression<StringType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayBoolean")
fun Boolean.inArray(array: CMList<Boolean>): InExpression<BooleanType> =
    toDopeType().inArray(array.toDopeType())

@JvmName("inArrayNumberConverter")
fun <KotlinType : Any, MapType : Number> KotlinType.inArray(array: CMConverterList<KotlinType, MapType>): InExpression<NumberType> =
    toDopeType(array).inArray(array.toDopeType())

@JvmName("inArrayStringConverter")
fun <KotlinType : Any> KotlinType.inArray(array: CMConverterList<KotlinType, String>): InExpression<StringType> =
    toDopeType(array).inArray(array.toDopeType())

@JvmName("inArrayBooleanConverter")
fun <KotlinType : Any> KotlinType.inArray(array: CMConverterList<KotlinType, Boolean>): InExpression<BooleanType> =
    toDopeType(array).inArray(array.toDopeType())

@JvmName("notInArrayNumber")
fun CMField<out Number>.notInArray(array: TypeExpression<ArrayType<NumberType>>): NotInExpression<NumberType> =
    toDopeType().notInArray(array)

@JvmName("notInArrayString")
fun CMField<String>.notInArray(array: TypeExpression<ArrayType<StringType>>): NotInExpression<StringType> =
    toDopeType().notInArray(array)

@JvmName("notInArrayBoolean")
fun CMField<Boolean>.notInArray(array: TypeExpression<ArrayType<BooleanType>>): NotInExpression<BooleanType> =
    toDopeType().notInArray(array)

@JvmName("notInArrayNumber")
fun TypeExpression<NumberType>.notInArray(array: CMList<out Number>): NotInExpression<NumberType> =
    this.notInArray(array.toDopeType())

@JvmName("notInArrayString")
fun TypeExpression<StringType>.notInArray(array: CMList<String>): NotInExpression<StringType> =
    this.notInArray(array.toDopeType())

@JvmName("notInArrayBoolean")
fun TypeExpression<BooleanType>.notInArray(array: CMList<Boolean>): NotInExpression<BooleanType> =
    this.notInArray(array.toDopeType())

@JvmName("notInArrayNumber")
fun CMField<out Number>.notInArray(array: CMList<out Number>): NotInExpression<NumberType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayString")
fun CMField<String>.notInArray(array: CMList<String>): NotInExpression<StringType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayBoolean")
fun CMField<Boolean>.notInArray(array: CMList<Boolean>): NotInExpression<BooleanType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayNumber")
fun CMField<out Number>.notInArray(array: Collection<TypeExpression<NumberType>>): NotInExpression<NumberType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayString")
fun CMField<String>.notInArray(array: Collection<TypeExpression<StringType>>): NotInExpression<StringType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayBoolean")
fun CMField<Boolean>.notInArray(array: Collection<TypeExpression<BooleanType>>): NotInExpression<BooleanType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayNumber")
fun Number.notInArray(array: CMList<out Number>): NotInExpression<NumberType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayString")
fun String.notInArray(array: CMList<String>): NotInExpression<StringType> =
    toDopeType().notInArray(array.toDopeType())

@JvmName("notInArrayBoolean")
fun Boolean.notInArray(array: CMList<Boolean>): NotInExpression<BooleanType> =
    toDopeType().notInArray(array.toDopeType())
