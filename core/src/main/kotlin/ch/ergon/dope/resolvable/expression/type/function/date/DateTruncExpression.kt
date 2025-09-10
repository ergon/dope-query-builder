package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class DateTruncMillisExpression(val date: TypeExpression<NumberType>, val dateUnit: DateUnit) :
    FunctionExpression<NumberType>("DATE_TRUNC_MILLIS", listOf(date, dateUnit))

data class DateTruncStrExpression(val date: TypeExpression<StringType>, val dateUnit: DateUnit) :
    FunctionExpression<StringType>("DATE_TRUNC_STR", listOf(date, dateUnit))

@JvmName("millisTruncTypeDateComponent")
fun TypeExpression<NumberType>.truncateTo(dateUnit: DateUnit) =
    DateTruncMillisExpression(this, dateUnit)

fun Number.truncateTo(dateUnit: DateUnit) =
    toDopeType().truncateTo(dateUnit)

@JvmName("strTruncTypeDateComponent")
fun TypeExpression<StringType>.truncateTo(dateUnit: DateUnit) =
    DateTruncStrExpression(this, dateUnit)

fun String.truncateTo(dateUnit: DateUnit) =
    toDopeType().truncateTo(dateUnit)
