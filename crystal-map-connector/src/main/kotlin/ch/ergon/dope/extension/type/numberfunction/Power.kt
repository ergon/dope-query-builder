package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.power
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMField

fun power(base: CMField<out Number>, exponent: CMField<out Number>) = power(base.toDopeType(), exponent.toDopeType())

fun power(base: CMField<out Number>, exponent: TypeExpression<NumberType>) = power(base.toDopeType(), exponent)

fun power(base: CMField<out Number>, exponent: Number) = power(base.toDopeType(), exponent)

fun power(base: TypeExpression<NumberType>, exponent: CMField<out Number>) = power(base, exponent.toDopeType())

fun power(base: Number, exponent: CMField<out Number>) = power(base, exponent.toDopeType())
