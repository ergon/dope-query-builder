package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatPathToQueryString
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.DopeSchemaArray
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList
import com.schwarz.crystalapi.schema.CMObject
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.CMType
import com.schwarz.crystalapi.schema.Schema

fun CMType.toDopeType(reference: String = path): Field<out ValidType> = Field(
    when (this) {
        is CMField<*> -> this.name
        is CMList<*> -> this.name
        is CMObjectList<*> -> this.name
        is CMObject<*> -> TODO("DOPE-216")
        else -> throw IllegalArgumentException("Unsupported type $this")
    },
    reference,
)

@JvmName("toDopeNumberField")
fun CMField<out Number>.toDopeType(reference: String = path): Field<NumberType> = Field(name, reference)

@JvmName("toDopeStringField")
fun CMField<String>.toDopeType(reference: String = path): Field<StringType> = Field(name, reference)

@JvmName("toDopeBooleanField")
fun CMField<Boolean>.toDopeType(reference: String = path): Field<BooleanType> = Field(name, reference)

@JvmName("toDopeNumberArrayField")
fun CMList<out Number>.toDopeType(): Field<ArrayType<NumberType>> = Field(name, path)

@JvmName("toDopeStringArrayField")
fun CMList<String>.toDopeType(): Field<ArrayType<StringType>> = Field(name, path)

@JvmName("toDopeBooleanArrayField")
fun CMList<Boolean>.toDopeType(): Field<ArrayType<BooleanType>> = Field(name, path)

fun CMList<out Any>.toDopeType(): Field<ArrayType<ValidType>> = Field(name, path)

// TODO: DOPE-192
fun <T : Schema> CMObjectList<T>.toDopeType() = DopeSchemaArray(element, formatPathToQueryString(name, path))
