package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.toArray
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("toArrayNumber")
fun toArray(field: CMField<out Number>) = toArray(field.toDopeType())

@JvmName("toArrayString")
fun toArray(field: CMField<String>) = toArray(field.toDopeType())

@JvmName("toArrayBoolean")
fun toArray(field: CMField<Boolean>) = toArray(field.toDopeType())

@JvmName("toArrayNumberList")
fun toArray(field: CMList<out Number>) = toArray(field.toDopeType())

@JvmName("toArrayStringList")
fun toArray(field: CMList<String>) = toArray(field.toDopeType())

@JvmName("toArrayBooleanList")
fun toArray(field: CMList<Boolean>) = toArray(field.toDopeType())
