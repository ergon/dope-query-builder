package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isBoolean
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("numberIsBoolean")
fun CMField<out Number>.isBoolean() = toDopeType().isBoolean()

@JvmName("stringIsBoolean")
fun CMField<String>.isBoolean() = toDopeType().isBoolean()

@JvmName("booleanIsBoolean")
fun CMField<Boolean>.isBoolean() = toDopeType().isBoolean()

@JvmName("numberListIsBoolean")
fun CMList<out Number>.isBoolean() = toDopeType().isBoolean()

@JvmName("stringListIsBoolean")
fun CMList<String>.isBoolean() = toDopeType().isBoolean()

@JvmName("booleanListIsBoolean")
fun CMList<Boolean>.isBoolean() = toDopeType().isBoolean()
