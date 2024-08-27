package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isAtom
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("numberIsAtom")
fun CMField<out Number>.isAtom() = toDopeType().isAtom()

@JvmName("stringIsAtom")
fun CMField<String>.isAtom() = toDopeType().isAtom()

@JvmName("booleanIsAtom")
fun CMField<Boolean>.isAtom() = toDopeType().isAtom()

@JvmName("numberListIsAtom")
fun CMList<out Number>.isAtom() = toDopeType().isAtom()

@JvmName("stringListIsAtom")
fun CMList<String>.isAtom() = toDopeType().isAtom()

@JvmName("booleanListIsAtom")
fun CMList<Boolean>.isAtom() = toDopeType().isAtom()
