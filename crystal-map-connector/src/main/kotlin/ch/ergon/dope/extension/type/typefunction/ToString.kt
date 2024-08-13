package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.toStr
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("numberToString")
fun CMField<out Number>.toStr() = toDopeType().toStr()

@JvmName("stringToString")
fun CMField<String>.toStr() = toDopeType().toStr()

@JvmName("booleanToString")
fun CMField<Boolean>.toStr() = toDopeType().toStr()

@JvmName("toStringNumberList")
fun CMList<out Number>.toStr() = toDopeType().toStr()

@JvmName("toStringStringList")
fun CMList<String>.toStr() = toDopeType().toStr()

@JvmName("toStringBooleanList")
fun CMList<Boolean>.toStr() = toDopeType().toStr()
