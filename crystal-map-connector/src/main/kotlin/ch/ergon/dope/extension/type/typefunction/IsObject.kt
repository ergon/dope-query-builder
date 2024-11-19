package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isObject
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("numberIsObject")
fun CMJsonField<out Number>.isObject() = toDopeType().isObject()

@JvmName("stringIsObject")
fun CMJsonField<String>.isObject() = toDopeType().isObject()

@JvmName("booleanIsObject")
fun CMJsonField<Boolean>.isObject() = toDopeType().isObject()

@JvmName("objectIsObject")
fun CMObjectField<Schema>.isObject() = toDopeType().isObject()

@JvmName("numberListIsObject")
fun CMJsonList<out Number>.isObject() = toDopeType().isObject()

@JvmName("stringListIsObject")
fun CMJsonList<String>.isObject() = toDopeType().isObject()

@JvmName("booleanListIsObject")
fun CMJsonList<Boolean>.isObject() = toDopeType().isObject()

@JvmName("objectListIsObject")
fun CMObjectList<Schema>.isObject() = toDopeType().isObject()
