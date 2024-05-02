package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.formatPathToQueryString
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.DopeSchemaArray
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("asNumberField")
fun CMField<out Number>.asField(reference: String = path): Field<NumberType> = Field(name, reference)

@JvmName("asStringField")
fun CMField<String>.asField(reference: String = path): Field<StringType> = Field(name, reference)

@JvmName("asBooleanField")
fun CMField<Boolean>.asField(reference: String = path): Field<BooleanType> = Field(name, reference)

@JvmName("asNumberArrayField")
fun CMList<out Number>.asArrayField(): Field<ArrayType<NumberType>> = Field(name, path)

@JvmName("asStringArrayField")
fun CMList<String>.asArrayField(): Field<ArrayType<StringType>> = Field(name, path)

@JvmName("asBooleanArrayField")
fun CMList<Boolean>.asArrayField(): Field<ArrayType<BooleanType>> = Field(name, path)

fun CMList<out Any>.asArrayField(): Field<ArrayType<ValidType>> = Field(name, path)

// TODO: DOPE-192
fun CMObjectList<Schema>.asSchemaArray() = DopeSchemaArray(element, formatPathToQueryString(name, path))
