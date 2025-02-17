package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class LowerExpression(inStr: TypeExpression<StringType>) : FunctionExpression<StringType>(
    "LOWER",
    inStr,
)

fun lower(inStr: TypeExpression<StringType>) = LowerExpression(inStr)

fun lower(inStr: String) = lower(inStr.toDopeType())
