package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

data class RtrimExpression(val inStr: TypeExpression<StringType>, val char: TypeExpression<StringType>? = null) :
    FunctionExpression<StringType>(listOf(inStr, char))

fun TypeExpression<StringType>.rtrim(char: TypeExpression<StringType>? = null) =
    RtrimExpression(this, char)

fun TypeExpression<StringType>.rtrim(char: String) = rtrim(char.toDopeType())

fun String.rtrim(char: TypeExpression<StringType>? = null) = toDopeType().rtrim(char)

fun String.rtrim(char: String) = toDopeType().rtrim(char.toDopeType())
