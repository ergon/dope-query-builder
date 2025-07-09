package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class DateFormatStrExpression(date: TypeExpression<StringType>, format: TypeExpression<StringType>) :
    FunctionExpression<StringType>("DATE_FORMAT_STR", date, format)

fun TypeExpression<StringType>.formatDate(format: TypeExpression<StringType>) = DateFormatStrExpression(this, format)

fun TypeExpression<StringType>.formatDate(format: String) = formatDate(format.toDopeType())

fun String.formatDate(format: TypeExpression<StringType>) = toDopeType().formatDate(format)

fun String.formatDate(format: String) = toDopeType().formatDate(format.toDopeType())
