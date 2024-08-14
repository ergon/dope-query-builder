package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.isNumber
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("numberIsNumber")
fun CMField<out Number>.isNumber() = toDopeType().isNumber()

@JvmName("stringIsNumber")
fun CMField<String>.isNumber() = toDopeType().isNumber()

@JvmName("booleanIsNumber")
fun CMField<Boolean>.isNumber() = toDopeType().isNumber()

@JvmName("numberListIsNumber")
fun CMList<out Number>.isNumber() = toDopeType().isNumber()

@JvmName("stringListIsNumber")
fun CMList<String>.isNumber() = toDopeType().isNumber()

@JvmName("booleanListIsNumber")
fun CMList<Boolean>.isNumber() = toDopeType().isNumber()
