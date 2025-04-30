package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class RpadExpression(
    inStr: TypeExpression<StringType>,
    size: TypeExpression<NumberType>,
    char: TypeExpression<StringType>? = null,
) : FunctionExpression<StringType>("RPAD", inStr, size, char)

fun rpad(
    inStr: TypeExpression<StringType>,
    size: TypeExpression<NumberType>,
    char: TypeExpression<StringType>? = null,
) = RpadExpression(inStr, size, char)

fun rpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, char: String) =
    rpad(inStr, size, char.toDopeType())

fun rpad(inStr: TypeExpression<StringType>, size: Number, char: TypeExpression<StringType>) =
    rpad(inStr, size.toDopeType(), char)

fun rpad(inStr: TypeExpression<StringType>, size: Number, char: String) =
    rpad(inStr, size.toDopeType(), char.toDopeType())

fun rpad(inStr: TypeExpression<StringType>, size: Number) = rpad(inStr, size.toDopeType())

fun rpad(inStr: String, size: TypeExpression<NumberType>, char: TypeExpression<StringType>) =
    rpad(inStr.toDopeType(), size, char)

fun rpad(inStr: String, size: TypeExpression<NumberType>) = rpad(inStr.toDopeType(), size)

fun rpad(inStr: String, size: TypeExpression<NumberType>, prefix: String) =
    rpad(inStr.toDopeType(), size, prefix.toDopeType())

fun rpad(inStr: String, size: Number, char: TypeExpression<StringType>) =
    rpad(inStr.toDopeType(), size.toDopeType(), char)

fun rpad(inStr: String, size: Number, char: String) =
    rpad(inStr.toDopeType(), size.toDopeType(), char.toDopeType())

fun rpad(inStr: String, size: Number) = rpad(inStr.toDopeType(), size.toDopeType())
