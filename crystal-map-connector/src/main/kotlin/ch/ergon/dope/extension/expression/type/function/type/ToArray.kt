package ch.ergon.dope.extension.expression.type.function.type

import ch.ergon.dope.resolvable.expression.type.function.type.toArray
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.Schema

@JvmName("numberToArray")
fun CMJsonField<out Number>.toArray() = toDopeType().toArray()

@JvmName("stringToArray")
fun CMJsonField<String>.toArray() = toDopeType().toArray()

@JvmName("booleanToArray")
fun CMJsonField<Boolean>.toArray() = toDopeType().toArray()

@JvmName("objectToArray")
fun CMObjectField<Schema>.toArray() = toDopeType().toArray()
