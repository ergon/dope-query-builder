package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class StrToMillisExpression(date: TypeExpression<StringType>, format: TypeExpression<StringType>? = null) :
    FunctionExpression<NumberType>("STR_TO_MILLIS", date, format)

fun TypeExpression<StringType>.toEpochMillis(format: TypeExpression<StringType>? = null) = StrToMillisExpression(this, format)

fun TypeExpression<StringType>.toEpochMillis(format: String) = StrToMillisExpression(this, format.toDopeType())

fun String.toEpochMillis(format: TypeExpression<StringType>? = null) = toDopeType().toEpochMillis(format)

fun String.toEpochMillis(format: String) = toDopeType().toEpochMillis(format.toDopeType())
