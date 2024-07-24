package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.toNumber
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("toNumberNumber")
fun toNumber(field: CMField<out Number>) = toNumber(field.toDopeType())

@JvmName("toNumberString")
fun toNumber(field: CMField<String>) = toNumber(field.toDopeType())

@JvmName("toNumberString")
fun toNumber(field: CMField<String>, string: String) = toNumber(field.toDopeType(), string.toDopeType())

@JvmName("toNumberString")
fun toNumber(field: CMField<String>, string: TypeExpression<StringType>) = toNumber(field.toDopeType(), string)

@JvmName("toNumberString")
fun toNumber(value: String, string: CMField<String>) = toNumber(value.toDopeType(), string.toDopeType())

@JvmName("toNumberString")
fun toNumber(value: TypeExpression<StringType>, string: CMField<String>) = toNumber(value, string.toDopeType())

@JvmName("toNumberString")
fun toNumber(field: CMField<String>, string: CMField<String>) = toNumber(field.toDopeType(), string.toDopeType())

@JvmName("toNumberBoolean")
fun toNumber(field: CMField<Boolean>) = toNumber(field.toDopeType())

@JvmName("toNumberNumberList")
fun toNumber(field: CMList<out Number>) = toNumber(field.toDopeType())

@JvmName("toNumberStringList")
fun toNumber(field: CMList<String>) = toNumber(field.toDopeType())

@JvmName("toNumberBooleanList")
fun toNumber(field: CMList<Boolean>) = toNumber(field.toDopeType())
