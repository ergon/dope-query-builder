package ch.ergon.dope.extension.type.arithmetic

import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.neg
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

fun neg(number: CMField<out Number>) = neg(number.toDopeType())
