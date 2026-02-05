package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

// Argument is called char, but is a string...
data class TrimExpression(
    val inStr: TypeExpression<StringType>,
    val char: TypeExpression<StringType>? = null,
) : FunctionExpression<StringType>(listOf(inStr, char))

fun TypeExpression<StringType>.trim(char: TypeExpression<StringType>? = null) =
    TrimExpression(this, char)

fun TypeExpression<StringType>.trim(char: String) = trim(char.toDopeType())

fun String.trim(char: TypeExpression<StringType>? = null) = toDopeType().trim(char)

fun String.trim(char: String) = toDopeType().trim(char.toDopeType())
