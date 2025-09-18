package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class MillisToStringExpression(
    val date: TypeExpression<NumberType>,
    val format: TypeExpression<StringType>? = null,
) : FunctionExpression<StringType>(listOf(date, format))

fun TypeExpression<NumberType>.toFormattedDate(format: TypeExpression<StringType>? = null) =
    MillisToStringExpression(this, format)

fun Number.toFormattedDate(format: TypeExpression<StringType>? = null) = toDopeType().toFormattedDate(format)

fun TypeExpression<NumberType>.toFormattedDate(format: String) = toFormattedDate(format.toDopeType())

fun Number.toFormattedDate(format: String) = toDopeType().toFormattedDate(format.toDopeType())
