package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.sign
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun sign(field: CMField<out Number>) = sign(field.toDopeType())
