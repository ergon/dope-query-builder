package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.isNumber
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("isNumberNumber")
fun isNumber(field: CMField<out Number>) = isNumber(field.toDopeType())

@JvmName("isNumberString")
fun isNumber(field: CMField<String>) = isNumber(field.toDopeType())

@JvmName("isNumberBoolean")
fun isNumber(field: CMField<Boolean>) = isNumber(field.toDopeType())

@JvmName("isNumberNumberList")
fun isNumber(field: CMList<out Number>) = isNumber(field.toDopeType())

@JvmName("isNumberStringList")
fun isNumber(field: CMList<String>) = isNumber(field.toDopeType())

@JvmName("isNumberBooleanList")
fun isNumber(field: CMList<Boolean>) = isNumber(field.toDopeType())
