package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

fun CMField<Number>.asNumberField(): Field<NumberType> = Field(name, path)

fun CMField<String>.asStringField(): Field<StringType> = Field(name, path)

fun CMField<Boolean>.asBooleanField(): Field<BooleanType> = Field(name, path)

fun CMList<out Any>.asArrayField(): Field<ArrayType> = Field(name, path)
