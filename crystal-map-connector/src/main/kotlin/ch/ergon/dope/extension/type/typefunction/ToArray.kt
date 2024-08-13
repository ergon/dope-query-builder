package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.toArray
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

@JvmName("numberToArray")
fun CMField<out Number>.toArray() = toDopeType().toArray()

@JvmName("stringToArray")
fun CMField<String>.toArray() = toDopeType().toArray()

@JvmName("booleanToArray")
fun CMField<Boolean>.toArray() = toDopeType().toArray()
