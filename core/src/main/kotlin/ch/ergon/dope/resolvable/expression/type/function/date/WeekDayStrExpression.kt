package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class WeekDayStrExpression(date: TypeExpression<StringType>) : FunctionExpression<StringType>("WEEKDAY_STR", date)

@JvmName("extractStrWeekdayName")
fun TypeExpression<StringType>.extractWeekdayName() =
    WeekDayStrExpression(this)

fun String.extractWeekdayName() =
    toDopeType().extractWeekdayName()
