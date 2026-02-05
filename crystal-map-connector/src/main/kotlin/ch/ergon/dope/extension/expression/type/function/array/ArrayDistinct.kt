package ch.ergon.dope.extension.expression.type.function.array

import ch.ergon.dope.resolvable.expression.type.function.array.distinct
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("numberArrayDistinct")
fun CMJsonList<Number>.distinct() = toDopeType().distinct()

@JvmName("stringArrayDistinct")
fun CMJsonList<String>.distinct() = toDopeType().distinct()

@JvmName("booleanArrayDistinct")
fun CMJsonList<Boolean>.distinct() = toDopeType().distinct()

@JvmName("objectArrayDistinct")
fun CMObjectList<Schema>.distinct() = toDopeType().distinct()
