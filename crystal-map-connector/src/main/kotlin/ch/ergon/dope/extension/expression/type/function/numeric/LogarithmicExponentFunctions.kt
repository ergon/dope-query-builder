package ch.ergon.dope.extension.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.exp
import ch.ergon.dope.resolvable.expression.type.function.numeric.ln
import ch.ergon.dope.resolvable.expression.type.function.numeric.log
import ch.ergon.dope.resolvable.expression.type.function.numeric.power
import ch.ergon.dope.resolvable.expression.type.function.numeric.sqrt
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMJsonField

fun exp(field: CMJsonField<out Number>) = exp(field.toDopeType())

fun log(field: CMJsonField<out Number>) = log(field.toDopeType())

fun ln(field: CMJsonField<out Number>) = ln(field.toDopeType())

fun power(base: CMJsonField<out Number>, exponent: CMJsonField<out Number>) = power(base.toDopeType(), exponent.toDopeType())

fun power(base: CMJsonField<out Number>, exponent: TypeExpression<NumberType>) = power(base.toDopeType(), exponent)

fun power(base: CMJsonField<out Number>, exponent: Number) = power(base.toDopeType(), exponent)

fun power(base: TypeExpression<NumberType>, exponent: CMJsonField<out Number>) = power(base, exponent.toDopeType())

fun power(base: Number, exponent: CMJsonField<out Number>) = power(base, exponent.toDopeType())

fun sqrt(field: CMJsonField<out Number>) = sqrt(field.toDopeType())
