package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.sign
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun sign(field: CMJsonField<out Number>) = sign(field.toDopeType())
