package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.random
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun random(field: CMJsonField<out Number>) = random(field.toDopeType())
