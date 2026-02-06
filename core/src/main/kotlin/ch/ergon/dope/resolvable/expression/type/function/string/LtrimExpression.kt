package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

data class LtrimExpression(val inStr: TypeExpression<StringType>, val char: TypeExpression<StringType>? = null) :
    FunctionExpression<StringType>(listOf(inStr, char))

fun TypeExpression<StringType>.ltrim(char: TypeExpression<StringType>? = null) =
    LtrimExpression(this, char)

fun TypeExpression<StringType>.ltrim(char: String) = ltrim(char.toDopeType())

fun String.ltrim(char: TypeExpression<StringType>? = null) = toDopeType().ltrim(char)

fun String.ltrim(char: String) = toDopeType().ltrim(char.toDopeType())
