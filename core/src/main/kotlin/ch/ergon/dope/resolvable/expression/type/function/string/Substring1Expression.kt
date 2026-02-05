package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class Substring1Expression(
    val inStr: TypeExpression<StringType>,
    val startPos: TypeExpression<NumberType>,
    val length: TypeExpression<NumberType>? = null,
) : FunctionExpression<StringType>(listOf(inStr, startPos, length))

fun TypeExpression<StringType>.substring1(
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = Substring1Expression(this, startPos, length)

fun TypeExpression<StringType>.substring1(startPos: TypeExpression<NumberType>, length: Int) =
    substring1(startPos, length.toDopeType())

fun TypeExpression<StringType>.substring1(startPos: Int, length: TypeExpression<NumberType>? = null) =
    substring1(startPos.toDopeType(), length)

fun TypeExpression<StringType>.substring1(startPos: Int, length: Int) =
    substring1(startPos.toDopeType(), length.toDopeType())

fun String.substring1(
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = toDopeType().substring1(startPos, length)

fun String.substring1(startPos: TypeExpression<NumberType>, length: Int) =
    toDopeType().substring1(startPos, length.toDopeType())

fun String.substring1(startPos: Int, length: TypeExpression<NumberType>? = null) =
    toDopeType().substring1(startPos.toDopeType(), length)

fun String.substring1(startPos: Int, length: Int) =
    toDopeType().substring1(startPos.toDopeType(), length.toDopeType())
