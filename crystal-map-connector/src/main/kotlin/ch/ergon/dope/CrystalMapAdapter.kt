package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatPathToQueryString
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.DopeSchemaArray
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

fun CMField<Number>.asNumberField(reference: String = path): Field<NumberType> = Field(name, reference)

fun CMField<String>.asStringField(reference: String = path): Field<StringType> = Field(name, reference)

fun CMField<Boolean>.asBooleanField(reference: String = path): Field<BooleanType> = Field(name, reference)

fun CMList<Number>.asNumberArrayField(): Field<ArrayType<NumberType>> = Field(name, path)

fun CMList<String>.asStringArrayField(): Field<ArrayType<StringType>> = Field(name, path)

fun CMList<Boolean>.asBooleanArrayField(): Field<ArrayType<BooleanType>> = Field(name, path)

fun <T : Schema> CMObjectList<T>.asSchemaArray() = DopeSchemaArray(element, formatPathToQueryString(name, path))
