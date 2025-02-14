package ch.ergon.dope.resolvable.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.StringType

class RtrimExpression(inStr: TypeExpression<StringType>, char: TypeExpression<StringType>? = null) :
    FunctionExpression<StringType>("RTRIM", inStr, char)

fun rtrim(inStr: TypeExpression<StringType>, char: TypeExpression<StringType>? = null) = RtrimExpression(inStr, char)

fun rtrim(inStr: TypeExpression<StringType>, char: String) = rtrim(inStr, char.toDopeType())

fun rtrim(inStr: String, char: TypeExpression<StringType>? = null) = rtrim(inStr.toDopeType(), char)

fun rtrim(inStr: String, char: String) = rtrim(inStr.toDopeType(), char.toDopeType())
