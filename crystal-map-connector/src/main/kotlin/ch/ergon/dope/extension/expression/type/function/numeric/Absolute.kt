package ch.ergon.dope.extension.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.function.numeric.abs
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun abs(field: CMJsonField<out Number>) = abs(field.toDopeType())
