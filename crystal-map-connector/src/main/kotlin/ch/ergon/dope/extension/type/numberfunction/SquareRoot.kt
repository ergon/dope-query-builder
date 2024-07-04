package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.sqrt
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun sqrt(field: CMField<out Number>) = sqrt(field.toDopeType())
