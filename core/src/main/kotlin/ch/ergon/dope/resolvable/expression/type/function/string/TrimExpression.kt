package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

// Argument is called char, but is a string...
class TrimExpression(
    inStr: TypeExpression<StringType>,
    char: TypeExpression<StringType>? = null,
) : FunctionExpression<StringType>("TRIM", inStr, char)

fun trim(inStr: TypeExpression<StringType>, char: TypeExpression<StringType>? = null) = TrimExpression(inStr, char)

fun trim(inStr: TypeExpression<StringType>, char: String) = trim(inStr, char.toDopeType())

fun trim(inStr: String, char: TypeExpression<StringType>? = null) = trim(inStr.toDopeType(), char)

fun trim(inStr: String, char: String) = trim(inStr.toDopeType(), char.toDopeType())
