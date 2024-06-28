package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.exp
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun exp(field: CMField<out Number>) = exp(field.toDopeType())
