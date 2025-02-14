package ch.ergon.dope.resolvable.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class MBSubstring1Expression(
    inStr: TypeExpression<StringType>,
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) : FunctionExpression<StringType>(
    "MB_SUBSTR1",
    inStr,
    startPos,
    length,
)

fun mbSubstring1(
    inStr: TypeExpression<StringType>,
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = MBSubstring1Expression(inStr, startPos, length)

fun mbSubstring1(inStr: TypeExpression<StringType>, startPos: TypeExpression<NumberType>, length: Int) =
    mbSubstring1(inStr, startPos, length.toDopeType())

fun mbSubstring1(inStr: TypeExpression<StringType>, startPos: Int, length: TypeExpression<NumberType>? = null) =
    mbSubstring1(inStr, startPos.toDopeType(), length)

fun mbSubstring1(inStr: String, startPos: TypeExpression<NumberType>, length: TypeExpression<NumberType>? = null) =
    mbSubstring1(inStr.toDopeType(), startPos, length)

fun mbSubstring1(inStr: TypeExpression<StringType>, startPos: Int, length: Int) =
    mbSubstring1(inStr, startPos.toDopeType(), length.toDopeType())

fun mbSubstring1(inStr: String, startPos: TypeExpression<NumberType>, length: Int) =
    mbSubstring1(inStr.toDopeType(), startPos, length.toDopeType())

fun mbSubstring1(inStr: String, startPos: Int, length: TypeExpression<NumberType>? = null) =
    mbSubstring1(inStr.toDopeType(), startPos.toDopeType(), length)

fun mbSubstring1(inStr: String, startPos: Int, length: Int) =
    mbSubstring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())
