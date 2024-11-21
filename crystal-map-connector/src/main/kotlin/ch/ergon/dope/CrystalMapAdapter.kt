package ch.ergon.dope

import ch.ergon.dope.extension.type.ObjectField
import ch.ergon.dope.extension.type.ObjectList
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMConverterList
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.CMType
import com.schwarz.crystalapi.schema.Schema
import kotlin.reflect.KClass

fun CMType.toDopeType(reference: String = path): Field<ValidType> = Field(name, reference)

@JvmName("toDopeTypeNumber")
fun <Convertable : Any, JsonType : Number> Convertable.toDopeType(other: CMConverterField<Convertable, JsonType>): TypeExpression<NumberType> =
    requireValidConvertable(other.typeConverter.write(this), Number::class).toDopeType()

@JvmName("toDopeTypeString")
fun <Convertable : Any> Convertable.toDopeType(other: CMConverterField<Convertable, String>): TypeExpression<StringType> =
    requireValidConvertable(other.typeConverter.write(this), String::class).toDopeType()

@JvmName("toDopeTypeBoolean")
fun <Convertable : Any> Convertable.toDopeType(other: CMConverterField<Convertable, Boolean>): TypeExpression<BooleanType> =
    requireValidConvertable(other.typeConverter.write(this), Boolean::class).toDopeType()

@JvmName("toDopeTypeListNumber")
fun <Convertable : Any, JsonType : Number> Convertable.toDopeType(other: CMConverterList<Convertable, JsonType>): TypeExpression<NumberType> =
    requireValidConvertable(other.typeConverter.write(this), Number::class).toDopeType()

@JvmName("toDopeTypeListString")
fun <Convertable : Any> Convertable.toDopeType(other: CMConverterList<Convertable, String>): TypeExpression<StringType> =
    requireValidConvertable(other.typeConverter.write(this), String::class).toDopeType()

@JvmName("toDopeTypeListBoolean")
fun <Convertable : Any> Convertable.toDopeType(other: CMConverterList<Convertable, Boolean>): TypeExpression<BooleanType> =
    requireValidConvertable(other.typeConverter.write(this), Boolean::class).toDopeType()

fun <Convertable : Any, JsonType : Number> CMConverterField<Convertable, JsonType>.toDopeType(other: Convertable) =
    requireValidConvertable(typeConverter.write(other), Number::class).toDopeType()

fun <Convertable : Any> CMConverterField<Convertable, String>.toDopeType(other: Convertable) =
    requireValidConvertable(typeConverter.write(other), String::class).toDopeType()

fun <Convertable : Any> CMConverterField<Convertable, Boolean>.toDopeType(other: Convertable) =
    requireValidConvertable(typeConverter.write(other), Boolean::class).toDopeType()

@JvmName("toDopeNumberField")
fun CMJsonField<out Number>.toDopeType(reference: String = path): Field<NumberType> = Field(name, reference)

@JvmName("toDopeStringField")
fun CMJsonField<String>.toDopeType(reference: String = path): Field<StringType> = Field(name, reference)

@JvmName("toDopeBooleanField")
fun CMJsonField<Boolean>.toDopeType(reference: String = path): Field<BooleanType> = Field(name, reference)

@JvmName("toDopeNumberArrayField")
fun CMJsonList<out Number>.toDopeType(): Field<ArrayType<NumberType>> = Field(name, path)

@JvmName("toDopeStringArrayField")
fun CMJsonList<String>.toDopeType(): Field<ArrayType<StringType>> = Field(name, path)

@JvmName("toDopeBooleanArrayField")
fun CMJsonList<Boolean>.toDopeType(): Field<ArrayType<BooleanType>> = Field(name, path)

fun CMJsonList<out Any>.toDopeType(): Field<ArrayType<ValidType>> = Field(name, path)

fun <S : Schema> CMObjectField<S>.toDopeType() = ObjectField(element, name, path)

fun <T : Schema> CMObjectList<T>.toDopeType() = ObjectList(element, name, path)

private fun <Convertable : Any, JsonType : Any> Convertable.requireValidConvertable(jsonType: JsonType?, jsonTypeClass: KClass<JsonType>) =
    requireNotNull(jsonType) {
        "Conversion failed: " +
            "The value of type '${this::class.simpleName}' couldn't be converted to the expected JSON type '${jsonTypeClass.simpleName}'. "
    }
