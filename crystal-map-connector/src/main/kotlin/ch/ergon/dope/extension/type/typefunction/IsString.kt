package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.isString
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("numberIsString")
fun CMField<out Number>.isString() = toDopeType().isString()

@JvmName("stringIsString")
fun CMField<String>.isString() = toDopeType().isString()

@JvmName("booleanIsString")
fun CMField<Boolean>.isString() = toDopeType().isString()

@JvmName("numberListIsString")
fun CMList<out Number>.isString() = toDopeType().isString()

@JvmName("stringListIsString")
fun CMList<String>.isString() = toDopeType().isString()

@JvmName("booleanListIsString")
fun CMList<Boolean>.isString() = toDopeType().isString()
