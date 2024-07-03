package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.ln
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun ln(field: CMField<out Number>) = ln(field.toDopeType())
