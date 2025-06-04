package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class DateTruncMillisExpression(date: TypeExpression<NumberType>, dateUnit: DateUnit) :
    FunctionExpression<NumberType>("DATE_TRUNC_MILLIS", date, dateUnit)

class DateTruncStrExpression(date: TypeExpression<StringType>, dateUnit: DateUnit) :
    FunctionExpression<StringType>("DATE_TRUNC_STR", date, dateUnit)

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
