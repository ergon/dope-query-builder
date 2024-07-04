package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.sin
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun sin(field: CMField<out Number>) = sin(field.toDopeType())
