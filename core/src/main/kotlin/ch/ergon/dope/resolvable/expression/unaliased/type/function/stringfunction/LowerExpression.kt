package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

class LowerExpression(inStr: TypeExpression<StringType>) : FunctionExpression<StringType>("LOWER", inStr)

fun lower(inStr: TypeExpression<StringType>) = LowerExpression(inStr)

fun lower(inStr: String) = LowerExpression(inStr.toDopeType())
