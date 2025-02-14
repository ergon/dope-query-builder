package ch.ergon.dope.extension.expression.single.type.function.type

import ch.ergon.dope.resolvable.expression.single.type.function.type.isArray
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("numberIsArray")
fun CMJsonField<out Number>.isArray() = toDopeType().isArray()

@JvmName("stringIsArray")
fun CMJsonField<String>.isArray() = toDopeType().isArray()

@JvmName("booleanIsArray")
fun CMJsonField<Boolean>.isArray() = toDopeType().isArray()

@JvmName("objectIsArray")
fun CMObjectField<Schema>.isArray() = toDopeType().isArray()

@JvmName("numberListIsArray")
fun CMJsonList<out Number>.isArray() = toDopeType().isArray()

@JvmName("stringListIsArray")
fun CMJsonList<String>.isArray() = toDopeType().isArray()

@JvmName("booleanListIsArray")
fun CMJsonList<Boolean>.isArray() = toDopeType().isArray()

@JvmName("objectListIsArray")
fun CMObjectList<Schema>.isArray() = toDopeType().isArray()
