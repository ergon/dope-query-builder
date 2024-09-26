package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
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
    RpadExpression(inStr, size, char.toDopeType())

fun rpad(inStr: TypeExpression<StringType>, size: Number, char: TypeExpression<StringType>) =
    RpadExpression(inStr, size.toDopeType(), char)

fun rpad(inStr: TypeExpression<StringType>, size: Number, char: String) =
    RpadExpression(inStr, size.toDopeType(), char.toDopeType())

fun rpad(inStr: TypeExpression<StringType>, size: Number) = RpadExpression(inStr, size.toDopeType())

fun rpad(inStr: String, size: TypeExpression<NumberType>, char: TypeExpression<StringType>) =
    RpadExpression(inStr.toDopeType(), size, char)

fun rpad(inStr: String, size: TypeExpression<NumberType>) = RpadExpression(inStr.toDopeType(), size)

fun rpad(inStr: String, size: TypeExpression<NumberType>, prefix: String) =
    RpadExpression(inStr.toDopeType(), size, prefix.toDopeType())

fun rpad(inStr: String, size: Number, char: TypeExpression<StringType>) =
    RpadExpression(inStr.toDopeType(), size.toDopeType(), char)

fun rpad(inStr: String, size: Number, char: String) =
    RpadExpression(inStr.toDopeType(), size.toDopeType(), char.toDopeType())

fun rpad(inStr: String, size: Number) = RpadExpression(inStr.toDopeType(), size.toDopeType())
