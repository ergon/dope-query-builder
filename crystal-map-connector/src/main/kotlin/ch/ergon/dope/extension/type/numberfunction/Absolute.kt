package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.abs
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun abs(field: CMJsonField<out Number>) = abs(field.toDopeType())
