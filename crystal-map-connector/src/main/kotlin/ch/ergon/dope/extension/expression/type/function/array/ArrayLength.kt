package ch.ergon.dope.extension.expression.type.function.array

import ch.ergon.dope.resolvable.expression.type.function.array.length
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("numberArrayLength")
fun CMJsonList<Number>.length() = toDopeType().length()

@JvmName("stringArrayLength")
fun CMJsonList<String>.length() = toDopeType().length()

@JvmName("booleanArrayLength")
fun CMJsonList<Boolean>.length() = toDopeType().length()

@JvmName("objectArrayLength")
fun CMObjectList<Schema>.length() = toDopeType().length()
