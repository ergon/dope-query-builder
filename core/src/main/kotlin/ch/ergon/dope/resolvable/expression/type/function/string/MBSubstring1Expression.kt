package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class MBSubstring1Expression(
    val inStr: TypeExpression<StringType>,
    val startPos: TypeExpression<NumberType>,
    val length: TypeExpression<NumberType>? = null,
) : FunctionExpression<StringType>(listOf(inStr, startPos, length))

fun TypeExpression<StringType>.mbSubstring1(
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = MBSubstring1Expression(this, startPos, length)

fun TypeExpression<StringType>.mbSubstring1(startPos: TypeExpression<NumberType>, length: Int) =
    mbSubstring1(startPos, length.toDopeType())

fun TypeExpression<StringType>.mbSubstring1(startPos: Int, length: TypeExpression<NumberType>? = null) =
    mbSubstring1(startPos.toDopeType(), length)

fun TypeExpression<StringType>.mbSubstring1(startPos: Int, length: Int) =
    mbSubstring1(startPos.toDopeType(), length.toDopeType())

fun String.mbSubstring1(
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = toDopeType().mbSubstring1(startPos, length)

fun String.mbSubstring1(startPos: TypeExpression<NumberType>, length: Int) =
    toDopeType().mbSubstring1(startPos, length.toDopeType())

fun String.mbSubstring1(startPos: Int, length: TypeExpression<NumberType>? = null) =
    toDopeType().mbSubstring1(startPos.toDopeType(), length)

fun String.mbSubstring1(startPos: Int, length: Int) =
    toDopeType().mbSubstring1(startPos.toDopeType(), length.toDopeType())
