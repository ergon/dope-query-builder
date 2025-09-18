package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class DateDiffMillisExpression(
    val date: TypeExpression<NumberType>,
    val other: TypeExpression<NumberType>,
    val dateUnit: DateUnit,
) : FunctionExpression<NumberType>(listOf(date, other, dateUnit))

data class DateDiffStrExpression(
    val date: TypeExpression<StringType>,
    val other: TypeExpression<StringType>,
    val dateUnit: DateUnit,
) : FunctionExpression<NumberType>(listOf(date, other, dateUnit))

@JvmName("millisDiffTypeDateComponent")
fun TypeExpression<NumberType>.differenceIn(
    other: TypeExpression<NumberType>,
    dateUnit: DateUnit,
) = DateDiffMillisExpression(this, other, dateUnit)

@JvmName("millisDiffNumberDateComponent")
fun TypeExpression<NumberType>.differenceIn(
    other: Number,
    dateUnit: DateUnit,
) = differenceIn(other.toDopeType(), dateUnit)

fun Number.differenceIn(
    other: TypeExpression<NumberType>,
    dateUnit: DateUnit,
) = toDopeType().differenceIn(other, dateUnit)

fun Number.differenceIn(
    other: Number,
    dateUnit: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), dateUnit)

@JvmName("strDiffTypeDateComponent")
fun TypeExpression<StringType>.differenceIn(
    other: TypeExpression<StringType>,
    dateUnit: DateUnit,
) = DateDiffStrExpression(this, other, dateUnit)

@JvmName("strDiffNumberDateComponent")
fun TypeExpression<StringType>.differenceIn(
    other: String,
    dateUnit: DateUnit,
) = differenceIn(other.toDopeType(), dateUnit)

fun String.differenceIn(
    other: TypeExpression<StringType>,
    dateUnit: DateUnit,
) = toDopeType().differenceIn(other, dateUnit)

fun String.differenceIn(
    other: String,
    dateUnit: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), dateUnit)
