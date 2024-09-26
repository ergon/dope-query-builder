package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

class RtrimExpression(inStr: TypeExpression<StringType>, char: TypeExpression<StringType>? = null) :
    FunctionExpression<StringType>("RTRIM", inStr, char)

fun rtrim(inStr: TypeExpression<StringType>, char: TypeExpression<StringType>? = null) = RtrimExpression(inStr, char)

fun rtrim(inStr: TypeExpression<StringType>, char: String) = RtrimExpression(inStr, char.toDopeType())

fun rtrim(inStr: String, char: TypeExpression<StringType>? = null) = RtrimExpression(inStr.toDopeType(), char)

fun rtrim(inStr: String, char: String) = RtrimExpression(inStr.toDopeType(), char.toDopeType())
