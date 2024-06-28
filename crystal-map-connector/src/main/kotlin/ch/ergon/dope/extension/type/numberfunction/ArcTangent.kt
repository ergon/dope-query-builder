package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.atan
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun atan(field: CMField<out Number>) = atan(field.toDopeType())
