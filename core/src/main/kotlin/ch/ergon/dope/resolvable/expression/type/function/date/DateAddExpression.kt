package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class DateAddMillisExpression(date: TypeExpression<NumberType>, increment: TypeExpression<NumberType>, dateUnit: DateUnit) :
    FunctionExpression<NumberType>("DATE_ADD_MILLIS", date, increment, dateUnit)

class DateAddStrExpression(date: TypeExpression<StringType>, increment: TypeExpression<NumberType>, dateUnit: DateUnit) :
    FunctionExpression<StringType>("DATE_ADD_STR", date, increment, dateUnit)

@JvmName("millisPlusTypeDateComponent")
fun TypeExpression<NumberType>.addDateUnit(
    increment: TypeExpression<NumberType>,
    dateUnit: DateUnit,
) = DateAddMillisExpression(this, increment, dateUnit)

@JvmName("millisPlusNumberDateComponent")
fun TypeExpression<NumberType>.addDateUnit(
    increment: Number,
    dateUnit: DateUnit,
) = addDateUnit(increment.toDopeType(), dateUnit)

fun Number.addDateUnit(
    increment: TypeExpression<NumberType>,
    dateUnit: DateUnit,
) = toDopeType().addDateUnit(increment, dateUnit)

fun Number.addDateUnit(
    increment: Number,
    dateUnit: DateUnit,
) = toDopeType().addDateUnit(increment.toDopeType(), dateUnit)

@JvmName("strPlusTypeDateComponent")
fun TypeExpression<StringType>.addDateUnit(
    increment: TypeExpression<NumberType>,
    dateUnit: DateUnit,
) = DateAddStrExpression(this, increment, dateUnit)

@JvmName("strPlusNumberDateComponent")
fun TypeExpression<StringType>.addDateUnit(
    increment: Number,
    dateUnit: DateUnit,
) = addDateUnit(increment.toDopeType(), dateUnit)

fun String.addDateUnit(
    increment: TypeExpression<NumberType>,
    dateUnit: DateUnit,
) = toDopeType().addDateUnit(increment, dateUnit)

fun String.addDateUnit(
    increment: Number,
    dateUnit: DateUnit,
) = toDopeType().addDateUnit(increment.toDopeType(), dateUnit)
