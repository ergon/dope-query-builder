package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class SubstringExpression(
    inStr: TypeExpression<StringType>,
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) : FunctionExpression<StringType>("SUBSTR", inStr, startPos, length)

fun substring(
    inStr: TypeExpression<StringType>,
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = SubstringExpression(inStr, startPos, length)

fun substring(inStr: TypeExpression<StringType>, startPos: TypeExpression<NumberType>, length: Int) =
    SubstringExpression(inStr, startPos, length.toDopeType())

fun substring(inStr: TypeExpression<StringType>, startPos: Int, length: TypeExpression<NumberType>? = null) =
    SubstringExpression(inStr, startPos.toDopeType(), length)

fun substring(inStr: String, startPos: TypeExpression<NumberType>, length: TypeExpression<NumberType>? = null) =
    SubstringExpression(inStr.toDopeType(), startPos, length)

fun substring(inStr: TypeExpression<StringType>, startPos: Int, length: Int) =
    SubstringExpression(inStr, startPos.toDopeType(), length.toDopeType())

fun substring(inStr: String, startPos: TypeExpression<NumberType>, length: Int) =
    SubstringExpression(inStr.toDopeType(), startPos, length.toDopeType())

fun substring(inStr: String, startPos: Int, length: TypeExpression<NumberType>? = null) =
    SubstringExpression(inStr.toDopeType(), startPos.toDopeType(), length)

fun substring(inStr: String, startPos: Int, length: Int) =
    SubstringExpression(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())
