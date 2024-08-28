package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.ceil
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.floor
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.round
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.trunc
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMField

fun ceil(field: CMField<out Number>) = ceil(field.toDopeType())

fun floor(field: CMField<out Number>) = floor(field.toDopeType())

fun round(field: CMField<out Number>) = round(field.toDopeType())

fun round(field: CMField<out Number>, digits: CMField<out Number>) = round(field.toDopeType(), digits.toDopeType())

fun round(field: CMField<out Number>, digits: TypeExpression<NumberType>) = round(field.toDopeType(), digits)

fun round(field: CMField<out Number>, digits: Number) = round(field.toDopeType(), digits)

fun round(value: TypeExpression<NumberType>, digits: CMField<out Number>) = round(value, digits.toDopeType())

fun round(value: Number, digits: CMField<out Number>) = round(value, digits.toDopeType())

fun trunc(field: CMField<out Number>) = trunc(field.toDopeType())

fun trunc(field: CMField<out Number>, digits: CMField<out Number>) = trunc(field.toDopeType(), digits.toDopeType())

fun trunc(field: CMField<out Number>, digits: TypeExpression<NumberType>) = trunc(field.toDopeType(), digits)

fun trunc(field: CMField<out Number>, digits: Number) = trunc(field.toDopeType(), digits)

fun trunc(value: TypeExpression<NumberType>, digits: CMField<out Number>) = trunc(value, digits.toDopeType())

fun trunc(value: Number, digits: CMField<out Number>) = trunc(value, digits.toDopeType())
