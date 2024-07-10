package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.acos
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun acos(field: CMField<out Number>) = acos(field.toDopeType())
