package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.isString
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("isStringNumber")
fun isString(field: CMField<out Number>) = isString(field.toDopeType())

@JvmName("isStringString")
fun isString(field: CMField<String>) = isString(field.toDopeType())

@JvmName("isStringBoolean")
fun isString(field: CMField<Boolean>) = isString(field.toDopeType())

@JvmName("isStringNumberList")
fun isString(field: CMList<out Number>) = isString(field.toDopeType())

@JvmName("isStringStringList")
fun isString(field: CMList<String>) = isString(field.toDopeType())

@JvmName("isStringBooleanList")
fun isString(field: CMList<Boolean>) = isString(field.toDopeType())
