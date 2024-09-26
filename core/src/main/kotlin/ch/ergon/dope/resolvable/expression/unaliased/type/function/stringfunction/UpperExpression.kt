package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

class UpperExpression(inStr: TypeExpression<StringType>) : FunctionExpression<StringType>("UPPER", inStr)

fun upper(inStr: TypeExpression<StringType>) = UpperExpression(inStr)

fun upper(inStr: String) = UpperExpression(inStr.toDopeType())
