package ch.ergon.dope.extension.expression.single.type.function.numeric

import ch.ergon.dope.resolvable.expression.single.type.function.numeric.sign
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun sign(field: CMJsonField<out Number>) = sign(field.toDopeType())
