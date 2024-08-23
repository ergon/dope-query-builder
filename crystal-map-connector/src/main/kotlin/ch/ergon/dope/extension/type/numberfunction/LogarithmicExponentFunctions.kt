package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.exp
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.ln
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.log
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.power
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.sqrt
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMField

fun exp(field: CMField<out Number>) = exp(field.toDopeType())

fun log(field: CMField<out Number>) = log(field.toDopeType())

fun ln(field: CMField<out Number>) = ln(field.toDopeType())

fun power(base: CMField<out Number>, exponent: CMField<out Number>) = power(base.toDopeType(), exponent.toDopeType())

fun power(base: CMField<out Number>, exponent: TypeExpression<NumberType>) = power(base.toDopeType(), exponent)

fun power(base: CMField<out Number>, exponent: Number) = power(base.toDopeType(), exponent)

fun power(base: TypeExpression<NumberType>, exponent: CMField<out Number>) = power(base, exponent.toDopeType())

fun power(base: Number, exponent: CMField<out Number>) = power(base, exponent.toDopeType())

fun sqrt(field: CMField<out Number>) = sqrt(field.toDopeType())
