package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.toBoolean
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("toBooleanNumber")
fun toBoolean(field: CMField<out Number>) = toBoolean(field.toDopeType())

@JvmName("toBooleanString")
fun toBoolean(field: CMField<String>) = toBoolean(field.toDopeType())

@JvmName("toBooleanBoolean")
fun toBoolean(field: CMField<Boolean>) = toBoolean(field.toDopeType())

@JvmName("toBooleanNumberList")
fun toBoolean(field: CMList<out Number>) = toBoolean(field.toDopeType())

@JvmName("toBooleanStringList")
fun toBoolean(field: CMList<String>) = toBoolean(field.toDopeType())

@JvmName("toBooleanBooleanList")
fun toBoolean(field: CMList<Boolean>) = toBoolean(field.toDopeType())
