package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toBool
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("numberToBoolean")
fun CMJsonField<out Number>.toBool() = toDopeType().toBool()

@JvmName("stringToBoolean")
fun CMJsonField<String>.toBool() = toDopeType().toBool()

@JvmName("booleanToBoolean")
fun CMJsonField<Boolean>.toBool() = toDopeType().toBool()

@JvmName("numberListToBoolean")
fun CMJsonList<out Number>.toBool() = toDopeType().toBool()

@JvmName("stringListToBoolean")
fun CMJsonList<String>.toBool() = toDopeType().toBool()

@JvmName("booleanListToBoolean")
fun CMJsonList<Boolean>.toBool() = toDopeType().toBool()
