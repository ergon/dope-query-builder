package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.toStr
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("numberToString")
fun CMJsonField<out Number>.toStr() = toDopeType().toStr()

@JvmName("stringToString")
fun CMJsonField<String>.toStr() = toDopeType().toStr()

@JvmName("booleanToString")
fun CMJsonField<Boolean>.toStr() = toDopeType().toStr()

@JvmName("toStringNumberList")
fun CMJsonList<out Number>.toStr() = toDopeType().toStr()

@JvmName("toStringStringList")
fun CMJsonList<String>.toStr() = toDopeType().toStr()

@JvmName("toStringBooleanList")
fun CMJsonList<Boolean>.toStr() = toDopeType().toStr()
