package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.floor
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun floor(field: CMField<out Number>) = floor(field.toDopeType())
