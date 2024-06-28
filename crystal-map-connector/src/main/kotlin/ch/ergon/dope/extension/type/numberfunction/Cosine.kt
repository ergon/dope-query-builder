package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.cos
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun cos(field: CMField<out Number>) = cos(field.toDopeType())
