package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class MillisToUtcExpression(date: TypeExpression<NumberType>, format: TypeExpression<StringType>? = null) : FunctionExpression<StringType>(
    "MILLIS_TO_UTC",
    date,
    format,
)

fun TypeExpression<NumberType>.toFormattedDateInUtc(format: TypeExpression<StringType>? = null) = MillisToUtcExpression(this, format)

fun Number.toFormattedDateInUtc(format: TypeExpression<StringType>? = null) = toDopeType().toFormattedDateInUtc(format)

fun TypeExpression<NumberType>.toFormattedDateInUtc(format: String) = toFormattedDateInUtc(format.toDopeType())

fun Number.toFormattedDateInUtc(format: String) = toDopeType().toFormattedDateInUtc(format.toDopeType())
