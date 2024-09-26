package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class MBSubstring1Expression(
    inStr: TypeExpression<StringType>,
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) : FunctionExpression<StringType>("MB_SUBSTR1", inStr, startPos, length)

fun mbSubstring1(
    inStr: TypeExpression<StringType>,
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = MBSubstring1Expression(inStr, startPos, length)

fun mbSubstring1(inStr: TypeExpression<StringType>, startPos: TypeExpression<NumberType>, length: Int) =
    MBSubstring1Expression(inStr, startPos, length.toDopeType())

fun mbSubstring1(inStr: TypeExpression<StringType>, startPos: Int, length: TypeExpression<NumberType>? = null) =
    MBSubstring1Expression(inStr, startPos.toDopeType(), length)

fun mbSubstring1(inStr: String, startPos: TypeExpression<NumberType>, length: TypeExpression<NumberType>? = null) =
    MBSubstring1Expression(inStr.toDopeType(), startPos, length)

fun mbSubstring1(inStr: TypeExpression<StringType>, startPos: Int, length: Int) =
    MBSubstring1Expression(inStr, startPos.toDopeType(), length.toDopeType())

fun mbSubstring1(inStr: String, startPos: TypeExpression<NumberType>, length: Int) =
    MBSubstring1Expression(inStr.toDopeType(), startPos, length.toDopeType())

fun mbSubstring1(inStr: String, startPos: Int, length: TypeExpression<NumberType>? = null) =
    MBSubstring1Expression(inStr.toDopeType(), startPos.toDopeType(), length)

fun mbSubstring1(inStr: String, startPos: Int, length: Int) =
    MBSubstring1Expression(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())
