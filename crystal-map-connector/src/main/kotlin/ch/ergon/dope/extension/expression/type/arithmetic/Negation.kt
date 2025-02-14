package ch.ergon.dope.extension.expression.type.arithmetic

import ch.ergon.dope.resolvable.expression.type.arithmetic.neg
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun neg(number: CMJsonField<out Number>) = neg(number.toDopeType())
