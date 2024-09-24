package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.ceil
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.floor
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.round
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.trunc
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMJsonField

fun ceil(field: CMJsonField<out Number>) = ceil(field.toDopeType())

fun floor(field: CMJsonField<out Number>) = floor(field.toDopeType())

fun round(field: CMJsonField<out Number>) = round(field.toDopeType())

fun round(field: CMJsonField<out Number>, digits: CMJsonField<out Number>) = round(field.toDopeType(), digits.toDopeType())

fun round(field: CMJsonField<out Number>, digits: TypeExpression<NumberType>) = round(field.toDopeType(), digits)

fun round(field: CMJsonField<out Number>, digits: Number) = round(field.toDopeType(), digits)

fun round(value: TypeExpression<NumberType>, digits: CMJsonField<out Number>) = round(value, digits.toDopeType())

fun round(value: Number, digits: CMJsonField<out Number>) = round(value, digits.toDopeType())

fun trunc(field: CMJsonField<out Number>) = trunc(field.toDopeType())

fun trunc(field: CMJsonField<out Number>, digits: CMJsonField<out Number>) = trunc(field.toDopeType(), digits.toDopeType())

fun trunc(field: CMJsonField<out Number>, digits: TypeExpression<NumberType>) = trunc(field.toDopeType(), digits)

fun trunc(field: CMJsonField<out Number>, digits: Number) = trunc(field.toDopeType(), digits)

fun trunc(value: TypeExpression<NumberType>, digits: CMJsonField<out Number>) = trunc(value, digits.toDopeType())

fun trunc(value: Number, digits: CMJsonField<out Number>) = trunc(value, digits.toDopeType())
