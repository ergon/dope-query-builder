package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class NowLocalExpression(format: TypeExpression<StringType>? = null) : FunctionExpression<StringType>("NOW_LOCAL", format)

fun localNowString(format: TypeExpression<StringType>? = null) = NowLocalExpression(format)

fun localNowString(format: String) = NowLocalExpression(format.toDopeType())
