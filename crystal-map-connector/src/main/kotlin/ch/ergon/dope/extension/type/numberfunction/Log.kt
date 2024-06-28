package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.log
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun log(field: CMField<out Number>) = log(field.toDopeType())
