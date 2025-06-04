package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateAddStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnit
import ch.ergon.dope.resolvable.expression.type.function.date.plusDateComponent
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("millisPlusCMNumberDateComponent")
fun CMJsonField<Number>.plusDateComponent(
    increment: CMJsonField<Number>,
    component: DateUnit,
) = toDopeType().plusDateComponent(increment.toDopeType(), component)

@JvmName("millisPlusTypeCMNumberDateComponent")
fun TypeExpression<NumberType>.plusDateComponent(
    increment: CMJsonField<Number>,
    component: DateUnit,
) = plusDateComponent(increment.toDopeType(), component)

@JvmName("millisPlusTypeCMNumberTypeDateComponent")
fun CMJsonField<Number>.plusDateComponent(
    increment: TypeExpression<NumberType>,
    component: DateUnit,
) = toDopeType().plusDateComponent(increment, component)

fun CMJsonField<Number>.plusDateComponent(
    increment: Number,
    component: DateUnit,
) = toDopeType().plusDateComponent(increment.toDopeType(), component)

fun Number.plusDateComponent(
    increment: CMJsonField<Number>,
    component: DateUnit,
) = toDopeType().plusDateComponent(increment.toDopeType(), component)

@JvmName("strPlusTypeDateComponent")
fun CMJsonField<String>.plusDateComponent(
    increment: CMJsonField<Number>,
    component: DateUnit,
): DateAddStrExpression = toDopeType().plusDateComponent(increment.toDopeType(), component)

@JvmName("strPlusStringDateComponent")
fun TypeExpression<StringType>.plusDateComponent(
    increment: CMJsonField<Number>,
    component: DateUnit,
): DateAddStrExpression = plusDateComponent(increment.toDopeType(), component)

fun CMJsonField<String>.plusDateComponent(
    increment: TypeExpression<NumberType>,
    component: DateUnit,
): DateAddStrExpression = toDopeType().plusDateComponent(increment, component)

fun CMJsonField<String>.plusDateComponent(
    increment: Number,
    component: DateUnit,
): DateAddStrExpression = toDopeType().plusDateComponent(increment.toDopeType(), component)

fun String.plusDateComponent(
    increment: CMJsonField<Number>,
    component: DateUnit,
): DateAddStrExpression = toDopeType().plusDateComponent(increment.toDopeType(), component)
