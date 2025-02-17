package ch.ergon.dope.extension.expression.type.function.type

import ch.ergon.dope.resolvable.expression.type.function.type.isAtom
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema

@JvmName("numberIsAtom")
fun CMJsonField<out Number>.isAtom() = toDopeType().isAtom()

@JvmName("stringIsAtom")
fun CMJsonField<String>.isAtom() = toDopeType().isAtom()

@JvmName("booleanIsAtom")
fun CMJsonField<Boolean>.isAtom() = toDopeType().isAtom()

@JvmName("objectIsAtom")
fun CMObjectField<Schema>.isAtom() = toDopeType().isAtom()

@JvmName("numberListIsAtom")
fun CMJsonList<out Number>.isAtom() = toDopeType().isAtom()

@JvmName("stringListIsAtom")
fun CMJsonList<String>.isAtom() = toDopeType().isAtom()

@JvmName("booleanListIsAtom")
fun CMJsonList<Boolean>.isAtom() = toDopeType().isAtom()

@JvmName("objectListIsAtom")
fun CMObjectList<Schema>.isAtom() = toDopeType().isAtom()
