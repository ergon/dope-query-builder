package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class WeekDayMillisExpression(
    val date: TypeExpression<NumberType>,
    val timeZone: TypeExpression<StringType>? = null,
) : FunctionExpression<StringType>(listOf(date, timeZone))

data class WeekDayStrExpression(val date: TypeExpression<StringType>) :
    FunctionExpression<StringType>(listOf(date))

fun TypeExpression<NumberType>.extractWeekdayName(timeZone: TypeExpression<StringType>? = null) =
    WeekDayMillisExpression(this, timeZone)

fun TypeExpression<NumberType>.extractWeekdayName(timeZone: String) = extractWeekdayName(timeZone.toDopeType())

fun Number.extractWeekdayName(timeZone: TypeExpression<StringType>? = null) = toDopeType().extractWeekdayName(timeZone)

fun Number.extractWeekdayName(timeZone: String) = toDopeType().extractWeekdayName(timeZone.toDopeType())

fun TypeExpression<StringType>.extractWeekdayName() = WeekDayStrExpression(this)

fun String.extractWeekdayName() = toDopeType().extractWeekdayName()
