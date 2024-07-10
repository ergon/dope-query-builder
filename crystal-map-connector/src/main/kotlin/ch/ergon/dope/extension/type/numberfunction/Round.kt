package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.round
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMField

fun round(field: CMField<out Number>) = round(field.toDopeType())

fun round(field: CMField<out Number>, digits: CMField<out Number>) = round(field.toDopeType(), digits.toDopeType())

fun round(field: CMField<out Number>, digits: TypeExpression<NumberType>) = round(field.toDopeType(), digits)

fun round(field: CMField<out Number>, digits: Number) = round(field.toDopeType(), digits)

fun round(value: TypeExpression<NumberType>, digits: CMField<out Number>) = round(value, digits.toDopeType())

fun round(value: Number, digits: CMField<out Number>) = round(value, digits.toDopeType())
