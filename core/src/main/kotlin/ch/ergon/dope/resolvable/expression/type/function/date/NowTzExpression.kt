package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class NowTzExpression(timeZone: TypeExpression<StringType>, format: TypeExpression<StringType>? = null) : FunctionExpression<StringType>(
    "NOW_TZ",
    timeZone,
    format,
)

fun nowStringInZone(timeZone: TypeExpression<StringType>, format: TypeExpression<StringType>? = null) =
    NowTzExpression(timeZone, format)

fun nowStringInZone(timeZone: String, format: TypeExpression<StringType>? = null) =
    nowStringInZone(timeZone.toDopeType(), format)

fun nowStringInZone(timeZone: TypeExpression<StringType>, format: String) =
    nowStringInZone(timeZone, format.toDopeType())

fun nowStringInZone(timeZone: String, format: String) =
    nowStringInZone(timeZone.toDopeType(), format.toDopeType())
