package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.tan
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun tan(field: CMField<out Number>) = tan(field.toDopeType())
