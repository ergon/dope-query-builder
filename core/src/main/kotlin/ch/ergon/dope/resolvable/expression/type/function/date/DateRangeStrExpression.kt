package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class DateRangeStrExpression(
    startDate: TypeExpression<StringType>,
    endDate: TypeExpression<StringType>,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) : FunctionExpression<ArrayType<StringType>>("DATE_RANGE_STR", startDate, endDate, interval, increment)

@JvmName("strRangeTypeDateComponent")
fun TypeExpression<StringType>.dateRangeBy(
    endDate: TypeExpression<StringType>,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = DateRangeStrExpression(this, endDate, interval, increment)

fun TypeExpression<StringType>.dateRangeBy(
    endDate: String,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = dateRangeBy(endDate.toDopeType(), interval, increment)

fun String.dateRangeBy(
    endDate: TypeExpression<StringType>,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate, interval, increment)

fun String.dateRangeBy(
    endDate: String,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment)

@JvmName("strRangeNumberDateComponent")
fun TypeExpression<StringType>.dateRangeBy(
    endDate: TypeExpression<StringType>,
    interval: DateComponent,
    increment: Number,
) = dateRangeBy(endDate, interval, increment.toDopeType())

fun TypeExpression<StringType>.dateRangeBy(
    endDate: String,
    interval: DateComponent,
    increment: Number,
) = dateRangeBy(endDate.toDopeType(), interval, increment.toDopeType())

fun String.dateRangeBy(
    endDate: TypeExpression<StringType>,
    interval: DateComponent,
    increment: Number,
) = toDopeType().dateRangeBy(endDate, interval, increment.toDopeType())

fun String.dateRangeBy(
    endDate: String,
    interval: DateComponent,
    increment: Number,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment.toDopeType())
