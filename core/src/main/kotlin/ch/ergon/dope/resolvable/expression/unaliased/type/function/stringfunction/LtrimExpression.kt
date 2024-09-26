package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

class LtrimExpression(inStr: TypeExpression<StringType>, char: TypeExpression<StringType>? = null) :
    FunctionExpression<StringType>("LTRIM", inStr, char)

fun ltrim(inStr: TypeExpression<StringType>, char: TypeExpression<StringType>? = null) =
    LtrimExpression(inStr, char)

fun ltrim(inStr: TypeExpression<StringType>, char: String) = LtrimExpression(inStr, char.toDopeType())

fun ltrim(inStr: String, char: TypeExpression<StringType>? = null) = LtrimExpression(inStr.toDopeType(), char)

fun ltrim(inStr: String, char: String) = LtrimExpression(inStr.toDopeType(), char.toDopeType())
