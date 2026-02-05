package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class RpadExpression(
    val inStr: TypeExpression<StringType>,
    val size: TypeExpression<NumberType>,
    val char: TypeExpression<StringType>? = null,
) : FunctionExpression<StringType>(listOf(inStr, size, char))

fun TypeExpression<StringType>.rpad(
    size: TypeExpression<NumberType>,
    char: TypeExpression<StringType>? = null,
) = RpadExpression(this, size, char)

fun TypeExpression<StringType>.rpad(size: TypeExpression<NumberType>, char: String) =
    rpad(size, char.toDopeType())

fun TypeExpression<StringType>.rpad(size: Number, char: TypeExpression<StringType>) =
    rpad(size.toDopeType(), char)

fun TypeExpression<StringType>.rpad(size: Number, char: String) =
    rpad(size.toDopeType(), char.toDopeType())

fun TypeExpression<StringType>.rpad(size: Number) = rpad(size.toDopeType())

fun String.rpad(
    size: TypeExpression<NumberType>,
    char: TypeExpression<StringType>? = null,
) = toDopeType().rpad(size, char)

fun String.rpad(size: TypeExpression<NumberType>, char: String) =
    toDopeType().rpad(size, char.toDopeType())

fun String.rpad(size: Number, char: TypeExpression<StringType>) =
    toDopeType().rpad(size.toDopeType(), char)

fun String.rpad(size: Number, char: String) =
    toDopeType().rpad(size.toDopeType(), char.toDopeType())

fun String.rpad(size: Number) = toDopeType().rpad(size.toDopeType())
