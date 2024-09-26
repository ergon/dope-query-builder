package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class MBLengthExpression(inStr: TypeExpression<StringType>) : FunctionExpression<NumberType>("MB_LENGTH", inStr)

fun mbLength(inStr: TypeExpression<StringType>) = MBLengthExpression(inStr)

fun mbLength(inStr: String) = MBLengthExpression(inStr.toDopeType())
