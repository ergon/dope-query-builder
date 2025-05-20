package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType

class DateRangeMillisExpression(
    startDate: TypeExpression<NumberType>,
    endDate: TypeExpression<NumberType>,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) : FunctionExpression<ArrayType<NumberType>>("DATE_RANGE_MILLIS", startDate, endDate, interval, increment)

@JvmName("millisRangeTypeDateComponent")
fun TypeExpression<NumberType>.dateRangeBy(
    endDate: TypeExpression<NumberType>,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = DateRangeMillisExpression(this, endDate, interval, increment)

fun TypeExpression<NumberType>.dateRangeBy(
    endDate: Number,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = dateRangeBy(endDate.toDopeType(), interval, increment)

fun Number.dateRangeBy(
    endDate: TypeExpression<NumberType>,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate, interval, increment)

fun Number.dateRangeBy(
    endDate: Number,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment)

@JvmName("millisRangeNumberDateComponent")
fun TypeExpression<NumberType>.dateRangeBy(
    endDate: TypeExpression<NumberType>,
    interval: DateComponent,
    increment: Number,
) = dateRangeBy(endDate, interval, increment.toDopeType())

fun TypeExpression<NumberType>.dateRangeBy(
    endDate: Number,
    interval: DateComponent,
    increment: Number,
) = dateRangeBy(endDate.toDopeType(), interval, increment.toDopeType())

fun Number.dateRangeBy(
    endDate: TypeExpression<NumberType>,
    interval: DateComponent,
    increment: Number,
) = toDopeType().dateRangeBy(endDate, interval, increment.toDopeType())

fun Number.dateRangeBy(
    endDate: Number,
    interval: DateComponent,
    increment: Number,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment.toDopeType())
