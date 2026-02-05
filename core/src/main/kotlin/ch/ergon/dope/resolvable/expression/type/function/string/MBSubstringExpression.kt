package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class MBSubstringExpression(
    val inStr: TypeExpression<StringType>,
    val startPos: TypeExpression<NumberType>,
    val length: TypeExpression<NumberType>? = null,
) : FunctionExpression<StringType>(listOf(inStr, startPos, length))

fun TypeExpression<StringType>.mbSubstring(
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = MBSubstringExpression(this, startPos, length)

fun TypeExpression<StringType>.mbSubstring(startPos: TypeExpression<NumberType>, length: Int) =
    mbSubstring(startPos, length.toDopeType())

fun TypeExpression<StringType>.mbSubstring(startPos: Int, length: TypeExpression<NumberType>? = null) =
    mbSubstring(startPos.toDopeType(), length)

fun TypeExpression<StringType>.mbSubstring(startPos: Int, length: Int) =
    mbSubstring(startPos.toDopeType(), length.toDopeType())

fun String.mbSubstring(
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = toDopeType().mbSubstring(startPos, length)

fun String.mbSubstring(startPos: TypeExpression<NumberType>, length: Int) =
    toDopeType().mbSubstring(startPos, length.toDopeType())

fun String.mbSubstring(startPos: Int, length: TypeExpression<NumberType>? = null) =
    toDopeType().mbSubstring(startPos.toDopeType(), length)

fun String.mbSubstring(startPos: Int, length: Int) =
    toDopeType().mbSubstring(startPos.toDopeType(), length.toDopeType())
