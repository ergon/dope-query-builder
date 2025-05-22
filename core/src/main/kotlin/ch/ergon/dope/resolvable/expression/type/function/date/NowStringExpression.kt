package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class NowStringExpression(format: TypeExpression<StringType>? = null) : FunctionExpression<StringType>("NOW_STR", format)

fun nowString(format: TypeExpression<StringType>? = null) = NowStringExpression(format)

fun nowString(format: String) = nowString(format.toDopeType())
