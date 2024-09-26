package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

class NowStringExpression(format: TypeExpression<StringType>? = null) :
    FunctionExpression<StringType>("NOW_STR", format)

// todo: DOPE-177
fun nowString(format: TypeExpression<StringType>? = null) = NowStringExpression(format)

fun nowString(format: String) = NowStringExpression(format.toDopeType())
