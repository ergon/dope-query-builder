package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class MBSubstringExpression(
    inStr: TypeExpression<StringType>,
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) : FunctionExpression<StringType>(
    "MB_SUBSTR",
    inStr,
    startPos,
    length,
)

fun mbSubstring(
    inStr: TypeExpression<StringType>,
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = MBSubstringExpression(inStr, startPos, length)

fun mbSubstring(inStr: TypeExpression<StringType>, startPos: TypeExpression<NumberType>, length: Int) =
    mbSubstring(inStr, startPos, length.toDopeType())

fun mbSubstring(inStr: TypeExpression<StringType>, startPos: Int, length: TypeExpression<NumberType>? = null) =
    mbSubstring(inStr, startPos.toDopeType(), length)

fun mbSubstring(inStr: String, startPos: TypeExpression<NumberType>, length: TypeExpression<NumberType>? = null) =
    mbSubstring(inStr.toDopeType(), startPos, length)

fun mbSubstring(inStr: TypeExpression<StringType>, startPos: Int, length: Int) =
    mbSubstring(inStr, startPos.toDopeType(), length.toDopeType())

fun mbSubstring(inStr: String, startPos: TypeExpression<NumberType>, length: Int) =
    mbSubstring(inStr.toDopeType(), startPos, length.toDopeType())

fun mbSubstring(inStr: String, startPos: Int, length: TypeExpression<NumberType>? = null) =
    mbSubstring(inStr.toDopeType(), startPos.toDopeType(), length)

fun mbSubstring(inStr: String, startPos: Int, length: Int) =
    mbSubstring(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())
