package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class SubstringExpression(
    val inStr: TypeExpression<StringType>,
    val startPos: TypeExpression<NumberType>,
    val length: TypeExpression<NumberType>? = null,
) : FunctionExpression<StringType>(listOf(inStr, startPos, length))

fun TypeExpression<StringType>.substring(
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = SubstringExpression(this, startPos, length)

fun TypeExpression<StringType>.substring(startPos: TypeExpression<NumberType>, length: Int) =
    substring(startPos, length.toDopeType())

fun TypeExpression<StringType>.substring(startPos: Int, length: TypeExpression<NumberType>? = null) =
    substring(startPos.toDopeType(), length)

fun TypeExpression<StringType>.substring(startPos: Int, length: Int) =
    substring(startPos.toDopeType(), length.toDopeType())

fun String.substring(
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = toDopeType().substring(startPos, length)

fun String.substring(startPos: TypeExpression<NumberType>, length: Int) =
    toDopeType().substring(startPos, length.toDopeType())

fun String.substring(startPos: Int, length: TypeExpression<NumberType>? = null) =
    toDopeType().substring(startPos.toDopeType(), length)

fun String.substring(startPos: Int, length: Int) =
    toDopeType().substring(startPos.toDopeType(), length.toDopeType())
