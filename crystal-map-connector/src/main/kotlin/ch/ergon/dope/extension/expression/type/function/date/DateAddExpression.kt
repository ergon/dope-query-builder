package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateAddStrExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateUnit
import ch.ergon.dope.resolvable.expression.type.function.date.addDateUnit
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("millisPlusCMNumberDateComponent")
fun CMJsonField<Number>.plusDateComponent(
    increment: CMJsonField<Number>,
    dateUnit: DateUnit,
) = toDopeType().addDateUnit(increment.toDopeType(), dateUnit)

@JvmName("millisPlusTypeCMNumberDateComponent")
fun TypeExpression<NumberType>.plusDateComponent(
    increment: CMJsonField<Number>,
    dateUnit: DateUnit,
) = addDateUnit(increment.toDopeType(), dateUnit)

@JvmName("millisPlusTypeCMNumberTypeDateComponent")
fun CMJsonField<Number>.plusDateComponent(
    increment: TypeExpression<NumberType>,
    dateUnit: DateUnit,
) = toDopeType().addDateUnit(increment, dateUnit)

fun CMJsonField<Number>.plusDateComponent(
    increment: Number,
    dateUnit: DateUnit,
) = toDopeType().addDateUnit(increment.toDopeType(), dateUnit)

fun Number.plusDateComponent(
    increment: CMJsonField<Number>,
    dateUnit: DateUnit,
) = toDopeType().addDateUnit(increment.toDopeType(), dateUnit)

@JvmName("strPlusTypeDateComponent")
fun CMJsonField<String>.plusDateComponent(
    increment: CMJsonField<Number>,
    dateUnit: DateUnit,
): DateAddStrExpression = toDopeType().addDateUnit(increment.toDopeType(), dateUnit)

@JvmName("strPlusStringDateComponent")
fun TypeExpression<StringType>.plusDateComponent(
    increment: CMJsonField<Number>,
    dateUnit: DateUnit,
): DateAddStrExpression = addDateUnit(increment.toDopeType(), dateUnit)

fun CMJsonField<String>.plusDateComponent(
    increment: TypeExpression<NumberType>,
    dateUnit: DateUnit,
): DateAddStrExpression = toDopeType().addDateUnit(increment, dateUnit)

fun CMJsonField<String>.plusDateComponent(
    increment: Number,
    dateUnit: DateUnit,
): DateAddStrExpression = toDopeType().addDateUnit(increment.toDopeType(), dateUnit)

fun String.plusDateComponent(
    increment: CMJsonField<Number>,
    dateUnit: DateUnit,
): DateAddStrExpression = toDopeType().addDateUnit(increment.toDopeType(), dateUnit)
