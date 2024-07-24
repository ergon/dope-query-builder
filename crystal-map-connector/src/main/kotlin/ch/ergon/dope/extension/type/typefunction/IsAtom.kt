package ch.ergon.dope.extension.type.typefunction

import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.isAtom
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("isAtomNumber")
fun isAtom(field: CMField<out Number>) = isAtom(field.toDopeType())

@JvmName("isAtomString")
fun isAtom(field: CMField<String>) = isAtom(field.toDopeType())

@JvmName("isAtomBoolean")
fun isAtom(field: CMField<Boolean>) = isAtom(field.toDopeType())

@JvmName("isAtomNumberList")
fun isAtom(field: CMList<out Number>) = isAtom(field.toDopeType())

@JvmName("isAtomStringList")
fun isAtom(field: CMList<String>) = isAtom(field.toDopeType())

@JvmName("isAtomBooleanList")
fun isAtom(field: CMList<Boolean>) = isAtom(field.toDopeType())
