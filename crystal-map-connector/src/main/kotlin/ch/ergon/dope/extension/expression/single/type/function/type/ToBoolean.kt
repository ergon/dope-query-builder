package ch.ergon.dope.extension.expression.single.type.function.type

import ch.ergon.dope.resolvable.expression.single.type.function.type.toBool
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("numberToBoolean")
fun CMJsonField<out Number>.toBool() = toDopeType().toBool()

@JvmName("stringToBoolean")
fun CMJsonField<String>.toBool() = toDopeType().toBool()

@JvmName("booleanToBoolean")
fun CMJsonField<Boolean>.toBool() = toDopeType().toBool()

@JvmName("objectToBoolean")
fun CMObjectField<Schema>.toBool() = toDopeType().toBool()

@JvmName("numberListToBoolean")
fun CMJsonList<out Number>.toBool() = toDopeType().toBool()

@JvmName("stringListToBoolean")
fun CMJsonList<String>.toBool() = toDopeType().toBool()

@JvmName("booleanListToBoolean")
fun CMJsonList<Boolean>.toBool() = toDopeType().toBool()

@JvmName("objectListToBoolean")
fun CMObjectList<Schema>.toBool() = toDopeType().toBool()
