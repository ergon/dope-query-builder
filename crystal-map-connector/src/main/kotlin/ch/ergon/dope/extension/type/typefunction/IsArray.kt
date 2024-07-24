package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.isArray
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("isArrayNumber")
fun isArray(field: CMField<out Number>) = isArray(field.toDopeType())

@JvmName("isArrayString")
fun isArray(field: CMField<String>) = isArray(field.toDopeType())

@JvmName("isArrayBoolean")
fun isArray(field: CMField<Boolean>) = isArray(field.toDopeType())

@JvmName("isArrayNumberList")
fun isArray(field: CMList<out Number>) = isArray(field.toDopeType())

@JvmName("isArrayStringList")
fun isArray(field: CMList<String>) = isArray(field.toDopeType())

@JvmName("isArrayBooleanList")
fun isArray(field: CMList<Boolean>) = isArray(field.toDopeType())
