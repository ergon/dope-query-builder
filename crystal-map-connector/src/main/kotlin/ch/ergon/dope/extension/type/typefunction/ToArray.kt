package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toArray
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("numberToArray")
fun CMJsonField<out Number>.toArray() = toDopeType().toArray()

@JvmName("stringToArray")
fun CMJsonField<String>.toArray() = toDopeType().toArray()

@JvmName("booleanToArray")
fun CMJsonField<Boolean>.toArray() = toDopeType().toArray()
