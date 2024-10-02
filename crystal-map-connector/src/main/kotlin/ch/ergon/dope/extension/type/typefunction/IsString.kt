package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isString
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("numberIsString")
fun CMJsonField<out Number>.isString() = toDopeType().isString()

@JvmName("stringIsString")
fun CMJsonField<String>.isString() = toDopeType().isString()

@JvmName("booleanIsString")
fun CMJsonField<Boolean>.isString() = toDopeType().isString()

@JvmName("objectIsString")
fun CMObjectField<Schema>.isString() = toDopeType().isString()

@JvmName("numberListIsString")
fun CMJsonList<out Number>.isString() = toDopeType().isString()

@JvmName("stringListIsString")
fun CMJsonList<String>.isString() = toDopeType().isString()

@JvmName("booleanListIsString")
fun CMJsonList<Boolean>.isString() = toDopeType().isString()

@JvmName("objectListIsString")
fun CMObjectList<Schema>.isString() = toDopeType().isString()
