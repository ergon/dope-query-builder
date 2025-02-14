package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class MBRpadExpression(
    inStr: TypeExpression<StringType>,
    size: TypeExpression<NumberType>,
    postfix: TypeExpression<StringType>? = null,
) : FunctionExpression<StringType>(
    "MB_RPAD",
    inStr,
    size,
    postfix,
)

fun mbRpad(
    inStr: TypeExpression<StringType>,
    size: TypeExpression<NumberType>,
    postfix: TypeExpression<StringType>? = null,
) = MBRpadExpression(inStr, size, postfix)

fun mbRpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, postfix: String) =
    mbRpad(inStr, size, postfix.toDopeType())

fun mbRpad(inStr: TypeExpression<StringType>, size: Number, postfix: TypeExpression<StringType>? = null) =
    mbRpad(inStr, size.toDopeType(), postfix)

fun mbRpad(inStr: TypeExpression<StringType>, size: Number, postfix: String) =
    mbRpad(inStr, size.toDopeType(), postfix.toDopeType())

fun mbRpad(inStr: String, size: TypeExpression<NumberType>, postfix: TypeExpression<StringType>? = null) =
    mbRpad(inStr.toDopeType(), size, postfix)

fun mbRpad(inStr: String, size: TypeExpression<NumberType>, prefix: String) =
    mbRpad(inStr.toDopeType(), size, prefix.toDopeType())

fun mbRpad(inStr: String, size: Number, postfix: TypeExpression<StringType>? = null) =
    mbRpad(inStr.toDopeType(), size.toDopeType(), postfix)

fun mbRpad(inStr: String, size: Number, postfix: String) =
    mbRpad(inStr.toDopeType(), size.toDopeType(), postfix.toDopeType())
