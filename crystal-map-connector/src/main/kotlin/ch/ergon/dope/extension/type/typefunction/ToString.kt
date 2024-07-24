package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.toString
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("toStringNumber")
fun toString(field: CMField<out Number>) = toString(field.toDopeType())

@JvmName("toStringString")
fun toString(field: CMField<String>) = toString(field.toDopeType())

@JvmName("toStringBoolean")
fun toString(field: CMField<Boolean>) = toString(field.toDopeType())

@JvmName("toStringNumberList")
fun toString(field: CMList<out Number>) = toString(field.toDopeType())

@JvmName("toStringStringList")
fun toString(field: CMList<String>) = toString(field.toDopeType())

@JvmName("toStringBooleanList")
fun toString(field: CMList<Boolean>) = toString(field.toDopeType())
