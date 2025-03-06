package ch.ergon.dope.extension.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.function.numeric.random
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun random(field: CMJsonField<out Number>) = random(field.toDopeType())
