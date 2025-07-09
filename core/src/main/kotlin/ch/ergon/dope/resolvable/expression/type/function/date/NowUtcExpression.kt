package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class NowUtcExpression(format: TypeExpression<StringType>? = null) : FunctionExpression<StringType>("NOW_UTC", format)

fun utcNowString(format: TypeExpression<StringType>? = null) =
    NowUtcExpression(format)

fun utcNowString(format: String) =
    utcNowString(format.toDopeType())
