package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.formatPathToQueryString
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.DopeSchemaArray
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMConverterField
import com.schwarz.crystalapi.schema.CMConverterList
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObject
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.CMType
import com.schwarz.crystalapi.schema.Schema

fun CMType.toDopeType(reference: String = path): Field<out ValidType> = Field(
    when (this) {
        is CMJsonField<*> -> this.name
        is CMJsonList<*> -> this.name
        is CMObjectList<*> -> this.name
        is CMObject<*> -> TODO("DOPE-216")
    },
    reference,
)

@JvmName("toDopeTypeNumber")
fun <Convertable : Any, JsonType : Number> Convertable.toDopeType(other: CMConverterField<Convertable, JsonType>): TypeExpression<NumberType> =
    other.typeConverter.write(this)!!.toDopeType()

@JvmName("toDopeTypeString")
fun <Convertable : Any> Convertable.toDopeType(other: CMConverterField<Convertable, String>): TypeExpression<StringType> =
    other.typeConverter.write(this)!!.toDopeType()

@JvmName("toDopeTypeBoolean")
fun <Convertable : Any> Convertable.toDopeType(other: CMConverterField<Convertable, Boolean>): TypeExpression<BooleanType> =
    other.typeConverter.write(this)!!.toDopeType()

@JvmName("toDopeTypeListNumber")
fun <Convertable : Any, JsonType : Number> Convertable.toDopeType(other: CMConverterList<Convertable, JsonType>): TypeExpression<NumberType> =
    other.typeConverter.write(this)!!.toDopeType()

@JvmName("toDopeTypeListString")
fun <Convertable : Any> Convertable.toDopeType(other: CMConverterList<Convertable, String>): TypeExpression<StringType> =
    other.typeConverter.write(this)!!.toDopeType()

@JvmName("toDopeTypeListBoolean")
fun <Convertable : Any> Convertable.toDopeType(other: CMConverterList<Convertable, Boolean>): TypeExpression<BooleanType> =
    other.typeConverter.write(this)!!.toDopeType()

fun <Convertable : Any, JsonType : Number> CMConverterField<Convertable, JsonType>.toDopeType(other: Convertable) =
    typeConverter.write(other)!!.toDopeType()

fun <Convertable : Any> CMConverterField<Convertable, String>.toDopeType(other: Convertable) = typeConverter.write(other)!!.toDopeType()

fun <Convertable : Any> CMConverterField<Convertable, Boolean>.toDopeType(other: Convertable) = typeConverter.write(other)!!.toDopeType()

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

// TODO: DOPE-192
fun <T : Schema> CMObjectList<T>.toDopeType() = DopeSchemaArray(element, formatPathToQueryString(name, path))
