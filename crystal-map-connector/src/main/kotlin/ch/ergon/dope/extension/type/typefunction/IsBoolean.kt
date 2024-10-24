package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isBoolean
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("numberIsBoolean")
fun CMJsonField<out Number>.isBoolean() = toDopeType().isBoolean()

@JvmName("stringIsBoolean")
fun CMJsonField<String>.isBoolean() = toDopeType().isBoolean()

@JvmName("booleanIsBoolean")
fun CMJsonField<Boolean>.isBoolean() = toDopeType().isBoolean()

@JvmName("objectIsBoolean")
fun CMObjectField<Schema>.isBoolean() = toDopeType().isBoolean()

@JvmName("numberListIsBoolean")
fun CMJsonList<out Number>.isBoolean() = toDopeType().isBoolean()

@JvmName("stringListIsBoolean")
fun CMJsonList<String>.isBoolean() = toDopeType().isBoolean()

@JvmName("booleanListIsBoolean")
fun CMJsonList<Boolean>.isBoolean() = toDopeType().isBoolean()

@JvmName("objectListIsBoolean")
fun CMObjectList<Schema>.isBoolean() = toDopeType().isBoolean()
