package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateComponent
import ch.ergon.dope.resolvable.expression.type.function.date.dateRangeBy
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("millisRangeCMNumberDateComponent")
fun CMJsonField<Number>.dateRangeBy(
    endDate: CMJsonField<Number>,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment)

fun CMJsonField<Number>.dateRangeBy(
    endDate: TypeExpression<NumberType>,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate, interval, increment)

@JvmName("millisRangeTypeCMNumberDateComponent")
fun TypeExpression<NumberType>.dateRangeBy(
    endDate: CMJsonField<Number>,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = dateRangeBy(endDate.toDopeType(), interval, increment)

fun CMJsonField<Number>.dateRangeBy(
    endDate: Number,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment)

fun Number.dateRangeBy(
    endDate: CMJsonField<Number>,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment)

fun Number.dateRangeBy(
    endDate: CMJsonField<Number>,
    interval: DateComponent,
    increment: Number,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment)

@JvmName("millisRangeCMNumberNumberDateComponent")
fun CMJsonField<Number>.dateRangeBy(
    endDate: CMJsonField<Number>,
    interval: DateComponent,
    increment: Number,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment.toDopeType())

fun TypeExpression<NumberType>.dateRangeBy(
    endDate: CMJsonField<Number>,
    interval: DateComponent,
    increment: Number,
) = dateRangeBy(endDate.toDopeType(), interval, increment.toDopeType())

fun CMJsonField<Number>.dateRangeBy(
    endDate: TypeExpression<NumberType>,
    interval: DateComponent,
    increment: Number,
) = toDopeType().dateRangeBy(endDate, interval, increment.toDopeType())

@JvmName("strRangeCMStringDateComponent")
fun CMJsonField<String>.dateRangeBy(
    endDate: CMJsonField<String>,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment)

@JvmName("strRangeTypeCMStringDateComponent")
fun TypeExpression<StringType>.dateRangeBy(
    endDate: CMJsonField<String>,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = dateRangeBy(endDate.toDopeType(), interval, increment)

@JvmName("strRangeCMStringTypeDateComponent")
fun CMJsonField<String>.dateRangeBy(
    endDate: TypeExpression<StringType>,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate, interval, increment)

fun CMJsonField<String>.dateRangeBy(
    endDate: String,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment)

fun String.dateRangeBy(
    endDate: CMJsonField<String>,
    interval: DateComponent,
    increment: TypeExpression<NumberType>? = null,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment)

@JvmName("strRangeCMStringNumberDateComponent")
fun CMJsonField<String>.dateRangeBy(
    endDate: CMJsonField<String>,
    interval: DateComponent,
    increment: Number,
) = toDopeType().dateRangeBy(endDate.toDopeType(), interval, increment.toDopeType())

fun TypeExpression<StringType>.dateRangeBy(
    endDate: CMJsonField<String>,
    interval: DateComponent,
    increment: Number,
) = dateRangeBy(endDate.toDopeType(), interval, increment.toDopeType())

fun CMJsonField<String>.dateRangeBy(
    endDate: TypeExpression<StringType>,
    interval: DateComponent,
    increment: Number,
) = toDopeType().dateRangeBy(endDate, interval, increment.toDopeType())
