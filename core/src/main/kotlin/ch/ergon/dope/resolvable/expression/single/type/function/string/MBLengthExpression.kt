package ch.ergon.dope.resolvable.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class MBLengthExpression(inStr: TypeExpression<StringType>) : FunctionExpression<NumberType>(
    "MB_LENGTH",
    inStr,
)

fun mbLength(inStr: TypeExpression<StringType>) = MBLengthExpression(inStr)

fun mbLength(inStr: String) = mbLength(inStr.toDopeType())
