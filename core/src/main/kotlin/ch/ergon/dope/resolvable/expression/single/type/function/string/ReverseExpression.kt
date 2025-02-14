package ch.ergon.dope.resolvable.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.StringType

class ReverseExpression(inStr: TypeExpression<StringType>) : FunctionExpression<StringType>(
    "REVERSE",
    inStr,
)

fun reverse(inStr: TypeExpression<StringType>) = ReverseExpression(inStr)

fun reverse(inStr: String) = reverse(inStr.toDopeType())
