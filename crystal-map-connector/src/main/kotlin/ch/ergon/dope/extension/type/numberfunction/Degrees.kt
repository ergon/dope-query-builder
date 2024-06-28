package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.degrees
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun degrees(field: CMField<out Number>) = degrees(field.toDopeType())
