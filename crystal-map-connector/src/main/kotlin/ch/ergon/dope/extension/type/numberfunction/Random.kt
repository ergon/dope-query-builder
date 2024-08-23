package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.random
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun random(field: CMField<out Number>) = random(field.toDopeType())
