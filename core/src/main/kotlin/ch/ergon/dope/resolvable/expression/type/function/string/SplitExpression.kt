package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType

data class SplitExpression(val inStr: TypeExpression<StringType>, val inSubstring: TypeExpression<StringType>? = null) :
    FunctionExpression<ArrayType<StringType>>(listOf(inStr, inSubstring))

fun TypeExpression<StringType>.split(inSubstring: TypeExpression<StringType>? = null) =
    SplitExpression(this, inSubstring)

fun TypeExpression<StringType>.split(inSubstring: String) = split(inSubstring.toDopeType())

fun String.split(inSubstring: TypeExpression<StringType>? = null) = toDopeType().split(inSubstring)

fun String.split(inSubstring: String) = toDopeType().split(inSubstring.toDopeType())
