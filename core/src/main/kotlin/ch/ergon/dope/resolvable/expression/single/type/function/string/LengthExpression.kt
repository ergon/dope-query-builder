package ch.ergon.dope.resolvable.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class LengthExpression(inStr: TypeExpression<StringType>) : FunctionExpression<NumberType>(
    "LENGTH",
    inStr,
)

fun length(inStr: TypeExpression<StringType>) = LengthExpression(inStr)

fun length(inStr: String) = length(inStr.toDopeType())
