package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.radians
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun radians(field: CMField<out Number>) = radians(field.toDopeType())
