package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class Substring1Expression(
    inStr: TypeExpression<StringType>,
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) : FunctionExpression<StringType>("SUBSTR1", inStr, startPos, length)

fun substring1(
    inStr: TypeExpression<StringType>,
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = Substring1Expression(inStr, startPos, length)

fun substring1(inStr: TypeExpression<StringType>, startPos: TypeExpression<NumberType>, length: Int) =
    substring1(inStr, startPos, length.toDopeType())

fun substring1(inStr: TypeExpression<StringType>, startPos: Int, length: TypeExpression<NumberType>? = null) =
    substring1(inStr, startPos.toDopeType(), length)

fun substring1(inStr: String, startPos: TypeExpression<NumberType>, length: TypeExpression<NumberType>? = null) =
    substring1(inStr.toDopeType(), startPos, length)

fun substring1(inStr: TypeExpression<StringType>, startPos: Int, length: Int) =
    substring1(inStr, startPos.toDopeType(), length.toDopeType())

fun substring1(inStr: String, startPos: TypeExpression<NumberType>, length: Int) =
    substring1(inStr.toDopeType(), startPos, length.toDopeType())

fun substring1(inStr: String, startPos: Int, length: TypeExpression<NumberType>? = null) =
    substring1(inStr.toDopeType(), startPos.toDopeType(), length)

fun substring1(inStr: String, startPos: Int, length: Int) =
    substring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())
