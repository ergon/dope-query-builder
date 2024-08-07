package ch.ergon.dope.extension.type.numberfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.acos
import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.asin
import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.atan
import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.atan2
import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.cos
import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.degrees
import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.radians
import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.sin
import ch.ergon.dope.resolvable.expression.unaliased.type.numeric.tan
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMField

fun acos(field: CMField<out Number>) = acos(field.toDopeType())

fun asin(field: CMField<out Number>) = asin(field.toDopeType())

fun atan(field: CMField<out Number>) = atan(field.toDopeType())

fun atan2(divisor: CMField<out Number>, dividend: CMField<out Number>) =
    atan2(divisor.toDopeType(), dividend.toDopeType())

fun atan2(divisor: CMField<out Number>, dividend: TypeExpression<NumberType>) =
    atan2(divisor.toDopeType(), dividend)

fun atan2(divisor: CMField<out Number>, dividend: Number) =
    atan2(divisor.toDopeType(), dividend)

fun atan2(divisor: TypeExpression<NumberType>, dividend: CMField<out Number>) =
    atan2(divisor, dividend.toDopeType())

fun atan2(divisor: Number, dividend: CMField<out Number>) =
    atan2(divisor, dividend.toDopeType())

fun cos(field: CMField<out Number>) = cos(field.toDopeType())

fun degrees(field: CMField<out Number>) = degrees(field.toDopeType())

fun radians(field: CMField<out Number>) = radians(field.toDopeType())

fun sin(field: CMField<out Number>) = sin(field.toDopeType())

fun tan(field: CMField<out Number>) = tan(field.toDopeType())
