package ch.ergon.dope.extension.expression.single.type.function.numeric

import ch.ergon.dope.resolvable.expression.single.type.function.numeric.abs
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun abs(field: CMJsonField<out Number>) = abs(field.toDopeType())
