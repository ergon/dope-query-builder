package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class MBLengthExpression(val inStr: TypeExpression<StringType>) :
    FunctionExpression<NumberType>(listOf(inStr))

fun mbLength(inStr: TypeExpression<StringType>) = MBLengthExpression(inStr)

fun mbLength(inStr: String) = mbLength(inStr.toDopeType())
