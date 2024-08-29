package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isAtom
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("numberIsAtom")
fun CMJsonField<out Number>.isAtom() = toDopeType().isAtom()

@JvmName("stringIsAtom")
fun CMJsonField<String>.isAtom() = toDopeType().isAtom()

@JvmName("booleanIsAtom")
fun CMJsonField<Boolean>.isAtom() = toDopeType().isAtom()

@JvmName("numberListIsAtom")
fun CMJsonList<out Number>.isAtom() = toDopeType().isAtom()

@JvmName("stringListIsAtom")
fun CMJsonList<String>.isAtom() = toDopeType().isAtom()

@JvmName("booleanListIsAtom")
fun CMJsonList<Boolean>.isAtom() = toDopeType().isAtom()
