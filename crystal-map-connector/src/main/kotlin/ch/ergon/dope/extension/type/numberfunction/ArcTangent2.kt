package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.atan2
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMField

fun atan2(divisor: CMField<out Number>, dividend: CMField<out Number>) = atan2(divisor.toDopeType(), dividend.toDopeType())

fun atan2(divisor: CMField<out Number>, dividend: TypeExpression<NumberType>) = atan2(divisor.toDopeType(), dividend)

fun atan2(divisor: CMField<out Number>, dividend: Number) = atan2(divisor.toDopeType(), dividend)

fun atan2(divisor: TypeExpression<NumberType>, dividend: CMField<out Number>) = atan2(divisor, dividend.toDopeType())

fun atan2(divisor: Number, dividend: CMField<out Number>) = atan2(divisor, dividend.toDopeType())
