package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.typeOf
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("typeOfNumber")
fun typeOf(field: CMField<out Number>) = typeOf(field.toDopeType())

@JvmName("typeOfString")
fun typeOf(field: CMField<String>) = typeOf(field.toDopeType())

@JvmName("typeOfBoolean")
fun typeOf(field: CMField<Boolean>) = typeOf(field.toDopeType())

@JvmName("typeOfNumberList")
fun typeOf(field: CMList<out Number>) = typeOf(field.toDopeType())

@JvmName("typeOfStringList")
fun typeOf(field: CMList<String>) = typeOf(field.toDopeType())

@JvmName("typeOfBooleanList")
fun typeOf(field: CMList<Boolean>) = typeOf(field.toDopeType())
