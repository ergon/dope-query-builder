package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class MillisExpression(date: TypeExpression<StringType>) : FunctionExpression<NumberType>("MILLIS", date)

fun TypeExpression<StringType>.toMillis() = MillisExpression(this)

fun String.toMillis() = toDopeType().toMillis()
