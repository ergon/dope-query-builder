package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.abs
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun abs(field: CMField<out Number>) = abs(field.toDopeType())
