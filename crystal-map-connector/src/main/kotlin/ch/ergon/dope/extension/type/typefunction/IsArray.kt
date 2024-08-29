package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isArray
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("numberIsArray")
fun CMJsonField<out Number>.isArray() = toDopeType().isArray()

@JvmName("stringIsArray")
fun CMJsonField<String>.isArray() = toDopeType().isArray()

@JvmName("booleanIsArray")
fun CMJsonField<Boolean>.isArray() = toDopeType().isArray()

@JvmName("numberListIsArray")
fun CMJsonList<out Number>.isArray() = toDopeType().isArray()

@JvmName("stringListIsArray")
fun CMJsonList<String>.isArray() = toDopeType().isArray()

@JvmName("booleanListIsArray")
fun CMJsonList<Boolean>.isArray() = toDopeType().isArray()
