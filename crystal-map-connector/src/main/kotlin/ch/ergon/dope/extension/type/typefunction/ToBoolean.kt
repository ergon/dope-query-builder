package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.toBool
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("numberToBoolean")
fun CMField<out Number>.toBool() = toDopeType().toBool()

@JvmName("stringToBoolean")
fun CMField<String>.toBool() = toDopeType().toBool()

@JvmName("booleanToBoolean")
fun CMField<Boolean>.toBool() = toDopeType().toBool()

@JvmName("numberListToBoolean")
fun CMList<out Number>.toBool() = toDopeType().toBool()

@JvmName("stringListToBoolean")
fun CMList<String>.toBool() = toDopeType().toBool()

@JvmName("booleanListToBoolean")
fun CMList<Boolean>.toBool() = toDopeType().toBool()
