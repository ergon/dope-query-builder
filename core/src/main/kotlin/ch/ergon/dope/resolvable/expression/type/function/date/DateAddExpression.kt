package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class DateAddMillisExpression(
    val date: TypeExpression<NumberType>,
    val increment: TypeExpression<NumberType>,
    val dateUnit: DateUnit,
) : FunctionExpression<NumberType>(listOf(date, increment, dateUnit))

data class DateAddStrExpression(
    val date: TypeExpression<StringType>,
    val increment: TypeExpression<NumberType>,
    val dateUnit: DateUnit,
) : FunctionExpression<StringType>(listOf(date, increment, dateUnit))

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
