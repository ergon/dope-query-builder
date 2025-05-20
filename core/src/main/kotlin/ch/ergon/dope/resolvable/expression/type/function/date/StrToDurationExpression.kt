package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class StrToDurationExpression(duration: TypeExpression<StringType>) : FunctionExpression<NumberType>("STR_TO_DURATION", duration)

fun TypeExpression<StringType>.toDurationMillis() = StrToDurationExpression(this)

fun String.toDurationMillis() = toDopeType().toDurationMillis()
