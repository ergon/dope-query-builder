package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.trunc
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMField

fun trunc(field: CMField<out Number>) = trunc(field.toDopeType())

fun trunc(field: CMField<out Number>, digits: CMField<out Number>) = trunc(field.toDopeType(), digits.toDopeType())

fun trunc(field: CMField<out Number>, digits: TypeExpression<NumberType>) = trunc(field.toDopeType(), digits)

fun trunc(field: CMField<out Number>, digits: Number) = trunc(field.toDopeType(), digits)

fun trunc(value: TypeExpression<NumberType>, digits: CMField<out Number>) = trunc(value, digits.toDopeType())

fun trunc(value: Number, digits: CMField<out Number>) = trunc(value, digits.toDopeType())
