package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class StrToTzExpression(date: TypeExpression<StringType>, timeZone: TypeExpression<StringType>) : FunctionExpression<StringType>(
    "STR_TO_TZ",
    date,
    timeZone,
)

fun TypeExpression<StringType>.toTimeZone(timeZone: TypeExpression<StringType>) = StrToTzExpression(this, timeZone)

fun TypeExpression<StringType>.toTimeZone(timeZone: String) = toTimeZone(timeZone.toDopeType())

fun String.toTimeZone(timeZone: TypeExpression<StringType>) = toDopeType().toTimeZone(timeZone)

fun String.toTimeZone(timeZone: String) = toDopeType().toTimeZone(timeZone.toDopeType())
