package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isNumber
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("numberIsNumber")
fun CMJsonField<out Number>.isNumber() = toDopeType().isNumber()

@JvmName("stringIsNumber")
fun CMJsonField<String>.isNumber() = toDopeType().isNumber()

@JvmName("booleanIsNumber")
fun CMJsonField<Boolean>.isNumber() = toDopeType().isNumber()

@JvmName("numberListIsNumber")
fun CMJsonList<out Number>.isNumber() = toDopeType().isNumber()

@JvmName("stringListIsNumber")
fun CMJsonList<String>.isNumber() = toDopeType().isNumber()

@JvmName("booleanListIsNumber")
fun CMJsonList<Boolean>.isNumber() = toDopeType().isNumber()
