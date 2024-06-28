package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.ceil
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun ceil(field: CMField<out Number>) = ceil(field.toDopeType())
