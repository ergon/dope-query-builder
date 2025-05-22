package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class MillisToTzExpression(date: TypeExpression<NumberType>, timeZone: TypeExpression<StringType>, format: TypeExpression<StringType>? = null) :
    FunctionExpression<StringType>("MILLIS_TO_TZ", date, timeZone, format)

class StrToTzExpression(date: TypeExpression<StringType>, timeZone: TypeExpression<StringType>) :
    FunctionExpression<StringType>("STR_TO_TZ", date, timeZone)

fun TypeExpression<NumberType>.toTimeZone(timeZone: TypeExpression<StringType>, format: TypeExpression<StringType>? = null) =
    MillisToTzExpression(this, timeZone, format)

fun Number.toTimeZone(timeZone: TypeExpression<StringType>, format: TypeExpression<StringType>? = null) =
    toDopeType().toTimeZone(timeZone, format)

fun TypeExpression<NumberType>.toTimeZone(timeZone: String, format: TypeExpression<StringType>? = null) =
    toTimeZone(timeZone.toDopeType(), format)

fun Number.toTimeZone(timeZone: String, format: TypeExpression<StringType>? = null) =
    toDopeType().toTimeZone(timeZone.toDopeType(), format)

fun TypeExpression<NumberType>.toTimeZone(timeZone: TypeExpression<StringType>, format: String) =
    toTimeZone(timeZone, format.toDopeType())

fun Number.toTimeZone(timeZone: TypeExpression<StringType>, format: String) =
    toDopeType().toTimeZone(timeZone, format.toDopeType())

fun TypeExpression<NumberType>.toTimeZone(timeZone: String, format: String) =
    toTimeZone(timeZone.toDopeType(), format.toDopeType())

fun Number.toTimeZone(timeZone: String, format: String) =
    toDopeType().toTimeZone(timeZone.toDopeType(), format.toDopeType())

fun TypeExpression<StringType>.toTimeZone(timeZone: TypeExpression<StringType>) = StrToTzExpression(this, timeZone)

fun TypeExpression<StringType>.toTimeZone(timeZone: String) = toTimeZone(timeZone.toDopeType())

fun String.toTimeZone(timeZone: TypeExpression<StringType>) = toDopeType().toTimeZone(timeZone)

fun String.toTimeZone(timeZone: String) = toDopeType().toTimeZone(timeZone.toDopeType())
