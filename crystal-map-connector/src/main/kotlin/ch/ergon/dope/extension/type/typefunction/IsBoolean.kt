package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.isBoolean
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("isBooleanNumber")
fun isBoolean(field: CMField<out Number>) = isBoolean(field.toDopeType())

@JvmName("isBooleanString")
fun isBoolean(field: CMField<String>) = isBoolean(field.toDopeType())

@JvmName("isBooleanBoolean")
fun isBoolean(field: CMField<Boolean>) = isBoolean(field.toDopeType())

@JvmName("isBooleanNumberList")
fun isBoolean(field: CMList<out Number>) = isBoolean(field.toDopeType())

@JvmName("isBooleanStringList")
fun isBoolean(field: CMList<String>) = isBoolean(field.toDopeType())

@JvmName("isBooleanBooleanList")
fun isBoolean(field: CMList<Boolean>) = isBoolean(field.toDopeType())
