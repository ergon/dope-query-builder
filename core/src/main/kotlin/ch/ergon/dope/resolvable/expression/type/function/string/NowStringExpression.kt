package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class NowStringExpression(format: TypeExpression<StringType>? = null) :
    FunctionExpression<StringType>("NOW_STR", format)

// todo: DOPE-177
fun nowString(format: TypeExpression<StringType>? = null) = NowStringExpression(format)

fun nowString(format: String) = nowString(format.toDopeType())
