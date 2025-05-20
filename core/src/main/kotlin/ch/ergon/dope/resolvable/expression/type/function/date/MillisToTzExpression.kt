package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class MillisToTzExpression(date: TypeExpression<NumberType>, timeZone: TypeExpression<StringType>, format: TypeExpression<StringType>? = null) :
    FunctionExpression<StringType>("MILLIS_TO_TZ", date, timeZone, format)

fun TypeExpression<NumberType>.toFormattedDateIn(timeZone: TypeExpression<StringType>, format: TypeExpression<StringType>? = null) =
    MillisToTzExpression(this, timeZone, format)

fun Number.toFormattedDateIn(timeZone: TypeExpression<StringType>, format: TypeExpression<StringType>? = null) =
    toDopeType().toFormattedDateIn(timeZone, format)

fun TypeExpression<NumberType>.toFormattedDateIn(timeZone: String, format: TypeExpression<StringType>? = null) =
    toFormattedDateIn(timeZone.toDopeType(), format)

fun Number.toFormattedDateIn(timeZone: String, format: TypeExpression<StringType>? = null) =
    toDopeType().toFormattedDateIn(timeZone.toDopeType(), format)

fun TypeExpression<NumberType>.toFormattedDateIn(timeZone: TypeExpression<StringType>, format: String) =
    toFormattedDateIn(timeZone, format.toDopeType())

fun Number.toFormattedDateIn(timeZone: TypeExpression<StringType>, format: String) =
    toDopeType().toFormattedDateIn(timeZone, format.toDopeType())

fun TypeExpression<NumberType>.toFormattedDateIn(timeZone: String, format: String) =
    toFormattedDateIn(timeZone.toDopeType(), format.toDopeType())

fun Number.toFormattedDateIn(timeZone: String, format: String) =
    toDopeType().toFormattedDateIn(timeZone.toDopeType(), format.toDopeType())
