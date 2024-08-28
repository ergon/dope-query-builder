package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isArray
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("numberIsArray")
fun CMField<out Number>.isArray() = toDopeType().isArray()

@JvmName("stringIsArray")
fun CMField<String>.isArray() = toDopeType().isArray()

@JvmName("booleanIsArray")
fun CMField<Boolean>.isArray() = toDopeType().isArray()

@JvmName("numberListIsArray")
fun CMList<out Number>.isArray() = toDopeType().isArray()

@JvmName("stringListIsArray")
fun CMList<String>.isArray() = toDopeType().isArray()

@JvmName("booleanListIsArray")
fun CMList<Boolean>.isArray() = toDopeType().isArray()
