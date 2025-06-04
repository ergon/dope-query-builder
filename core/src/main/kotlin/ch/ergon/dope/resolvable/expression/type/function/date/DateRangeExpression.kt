package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class DateRangeMillisExpression(
    startDate: TypeExpression<NumberType>,
    endDate: TypeExpression<NumberType>,
    interval: DateUnit,
    increment: TypeExpression<NumberType>? = null,
) : FunctionExpression<ArrayType<NumberType>>("DATE_RANGE_MILLIS", startDate, endDate, interval, increment)

class DateRangeStrExpression(
    startDate: TypeExpression<StringType>,
    endDate: TypeExpression<StringType>,
    interval: DateUnit,
    increment: TypeExpression<NumberType>? = null,
) : FunctionExpression<ArrayType<StringType>>("DATE_RANGE_STR", startDate, endDate, interval, increment)

@JvmName("millisRangeTypeDateComponent")
fun TypeExpression<NumberType>.dateRangeBy(
    endDate: TypeExpression<NumberType>,
    interval: DateUnit,
    increment: TypeExpression<NumberType>? = null,
) = DateRangeMillisExpression(this, endDate, interval, increment)

fun TypeExpression<NumberType>.dateRangeBy(
    endDate: Number,
    interval: DateUnit,
    increment: TypeExpression<NumberType>? = null,
) = dateRangeBy(endDate.toDopeType(), interval, increment)

fun Number.dateRangeBy(
    endDate: TypeExpression<NumberType>,
    interval: DateUnit,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate, interval, increment)

fun Number.dateRangeBy(
    endDate: Number,
    interval: DateUnit,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment)

@JvmName("millisRangeNumberDateComponent")
fun TypeExpression<NumberType>.dateRangeBy(
    endDate: TypeExpression<NumberType>,
    interval: DateUnit,
    increment: Number,
) = dateRangeBy(endDate, interval, increment.toDopeType())

fun TypeExpression<NumberType>.dateRangeBy(
    endDate: Number,
    interval: DateUnit,
    increment: Number,
) = dateRangeBy(endDate.toDopeType(), interval, increment.toDopeType())

fun Number.dateRangeBy(
    endDate: TypeExpression<NumberType>,
    interval: DateUnit,
    increment: Number,
) = toDopeType().dateRangeBy(endDate, interval, increment.toDopeType())

fun Number.dateRangeBy(
    endDate: Number,
    interval: DateUnit,
    increment: Number,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment.toDopeType())

@JvmName("strRangeTypeDateComponent")
fun TypeExpression<StringType>.dateRangeBy(
    endDate: TypeExpression<StringType>,
    interval: DateUnit,
    increment: TypeExpression<NumberType>? = null,
) = DateRangeStrExpression(this, endDate, interval, increment)

fun TypeExpression<StringType>.dateRangeBy(
    endDate: String,
    interval: DateUnit,
    increment: TypeExpression<NumberType>? = null,
) = dateRangeBy(endDate.toDopeType(), interval, increment)

fun String.dateRangeBy(
    endDate: TypeExpression<StringType>,
    interval: DateUnit,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate, interval, increment)

fun String.dateRangeBy(
    endDate: String,
    interval: DateUnit,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment)

@JvmName("strRangeNumberDateComponent")
fun TypeExpression<StringType>.dateRangeBy(
    endDate: TypeExpression<StringType>,
    interval: DateUnit,
    increment: Number,
) = dateRangeBy(endDate, interval, increment.toDopeType())

fun TypeExpression<StringType>.dateRangeBy(
    endDate: String,
    interval: DateUnit,
    increment: Number,
) = dateRangeBy(endDate.toDopeType(), interval, increment.toDopeType())

fun String.dateRangeBy(
    endDate: TypeExpression<StringType>,
    interval: DateUnit,
    increment: Number,
) = toDopeType().dateRangeBy(endDate, interval, increment.toDopeType())

fun String.dateRangeBy(
    endDate: String,
    interval: DateUnit,
    increment: Number,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment.toDopeType())
