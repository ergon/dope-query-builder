package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class StrToUtcExpression(date: TypeExpression<StringType>) : FunctionExpression<StringType>("STR_TO_UTC", date)

fun TypeExpression<StringType>.toUtcDate() = StrToUtcExpression(this)

fun String.toUtcDate() = toDopeType().toUtcDate()
