package ch.ergon.dope.resolvable.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.StringType

class LtrimExpression(inStr: TypeExpression<StringType>, char: TypeExpression<StringType>? = null) :
    FunctionExpression<StringType>("LTRIM", inStr, char)

fun ltrim(inStr: TypeExpression<StringType>, char: TypeExpression<StringType>? = null) =
    LtrimExpression(inStr, char)

fun ltrim(inStr: TypeExpression<StringType>, char: String) = ltrim(inStr, char.toDopeType())

fun ltrim(inStr: String, char: TypeExpression<StringType>? = null) = ltrim(inStr.toDopeType(), char)

fun ltrim(inStr: String, char: String) = ltrim(inStr.toDopeType(), char.toDopeType())
