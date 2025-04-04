package ch.ergon.dope.extension.expression.type.function.type

import ch.ergon.dope.resolvable.expression.type.function.type.isNumber
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("numberIsNumber")
fun CMJsonField<out Number>.isNumber() = toDopeType().isNumber()

@JvmName("stringIsNumber")
fun CMJsonField<String>.isNumber() = toDopeType().isNumber()

@JvmName("booleanIsNumber")
fun CMJsonField<Boolean>.isNumber() = toDopeType().isNumber()

@JvmName("objectIsNumber")
fun CMObjectField<Schema>.isNumber() = toDopeType().isNumber()

@JvmName("numberListIsNumber")
fun CMJsonList<out Number>.isNumber() = toDopeType().isNumber()

@JvmName("stringListIsNumber")
fun CMJsonList<String>.isNumber() = toDopeType().isNumber()

@JvmName("booleanListIsNumber")
fun CMJsonList<Boolean>.isNumber() = toDopeType().isNumber()

@JvmName("objectListIsNumber")
fun CMObjectList<Schema>.isNumber() = toDopeType().isNumber()
