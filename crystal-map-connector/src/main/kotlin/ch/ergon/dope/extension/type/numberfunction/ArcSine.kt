package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.asin
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun asin(field: CMField<out Number>) = asin(field.toDopeType())
