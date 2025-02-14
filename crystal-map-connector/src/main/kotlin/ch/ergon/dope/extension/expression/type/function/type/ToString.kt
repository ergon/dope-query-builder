package ch.ergon.dope.extension.expression.type.function.type

import ch.ergon.dope.resolvable.expression.type.function.type.toStr
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("numberToString")
fun CMJsonField<out Number>.toStr() = toDopeType().toStr()

@JvmName("stringToString")
fun CMJsonField<String>.toStr() = toDopeType().toStr()

@JvmName("booleanToString")
fun CMJsonField<Boolean>.toStr() = toDopeType().toStr()

@JvmName("objectToString")
fun CMObjectField<Schema>.toStr() = toDopeType().toStr()

@JvmName("toStringNumberList")
fun CMJsonList<out Number>.toStr() = toDopeType().toStr()

@JvmName("toStringStringList")
fun CMJsonList<String>.toStr() = toDopeType().toStr()

@JvmName("toStringBooleanList")
fun CMJsonList<Boolean>.toStr() = toDopeType().toStr()

@JvmName("objectListToString")
fun CMObjectList<Schema>.toStr() = toDopeType().toStr()
