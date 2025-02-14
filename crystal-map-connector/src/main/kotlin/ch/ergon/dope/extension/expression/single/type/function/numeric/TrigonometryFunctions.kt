package ch.ergon.dope.extension.expression.single.type.function.numeric

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.numeric.acos
import ch.ergon.dope.resolvable.expression.single.type.function.numeric.asin
import ch.ergon.dope.resolvable.expression.single.type.function.numeric.atan
import ch.ergon.dope.resolvable.expression.single.type.function.numeric.atan2
import ch.ergon.dope.resolvable.expression.single.type.function.numeric.cos
import ch.ergon.dope.resolvable.expression.single.type.function.numeric.degrees
import ch.ergon.dope.resolvable.expression.single.type.function.numeric.radians
import ch.ergon.dope.resolvable.expression.single.type.function.numeric.sin
import ch.ergon.dope.resolvable.expression.single.type.function.numeric.tan
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMJsonField

fun acos(field: CMJsonField<out Number>) = acos(field.toDopeType())

fun asin(field: CMJsonField<out Number>) = asin(field.toDopeType())

fun atan(field: CMJsonField<out Number>) = atan(field.toDopeType())

fun atan2(divisor: CMJsonField<out Number>, dividend: CMJsonField<out Number>) =
    atan2(divisor.toDopeType(), dividend.toDopeType())

fun atan2(divisor: CMJsonField<out Number>, dividend: TypeExpression<NumberType>) =
    atan2(divisor.toDopeType(), dividend)

fun atan2(divisor: CMJsonField<out Number>, dividend: Number) =
    atan2(divisor.toDopeType(), dividend)

fun atan2(divisor: TypeExpression<NumberType>, dividend: CMJsonField<out Number>) =
    atan2(divisor, dividend.toDopeType())

fun atan2(divisor: Number, dividend: CMJsonField<out Number>) =
    atan2(divisor, dividend.toDopeType())

fun cos(field: CMJsonField<out Number>) = cos(field.toDopeType())

fun degrees(field: CMJsonField<out Number>) = degrees(field.toDopeType())

fun radians(field: CMJsonField<out Number>) = radians(field.toDopeType())

fun sin(field: CMJsonField<out Number>) = sin(field.toDopeType())

fun tan(field: CMJsonField<out Number>) = tan(field.toDopeType())
