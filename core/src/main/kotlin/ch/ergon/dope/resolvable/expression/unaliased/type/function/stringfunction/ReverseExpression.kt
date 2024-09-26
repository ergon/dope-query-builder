package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

class ReverseExpression(inStr: TypeExpression<StringType>) : FunctionExpression<StringType>("REVERSE", inStr)

fun reverse(inStr: TypeExpression<StringType>) = ReverseExpression(inStr)

fun reverse(inStr: String) = ReverseExpression(inStr.toDopeType())
