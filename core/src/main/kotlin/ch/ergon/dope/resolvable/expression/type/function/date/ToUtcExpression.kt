package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class MillisToUtcExpression(val date: TypeExpression<NumberType>, val format: TypeExpression<StringType>? = null) :
    FunctionExpression<StringType>("MILLIS_TO_UTC", listOf(date, format))

data class StrToUtcExpression(val date: TypeExpression<StringType>) :
    FunctionExpression<StringType>("STR_TO_UTC", listOf(date))

fun TypeExpression<NumberType>.toUtcDate(format: TypeExpression<StringType>? = null) =
    MillisToUtcExpression(this, format)

fun Number.toUtcDate(format: TypeExpression<StringType>? = null) = toDopeType().toUtcDate(format)

fun TypeExpression<NumberType>.toUtcDate(format: String) = toUtcDate(format.toDopeType())

fun Number.toUtcDate(format: String) = toDopeType().toUtcDate(format.toDopeType())

fun TypeExpression<StringType>.toUtcDate() = StrToUtcExpression(this)

fun String.toUtcDate() = toDopeType().toUtcDate()
