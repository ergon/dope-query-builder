package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class MBLpadExpression(
    inStr: TypeExpression<StringType>,
    size: TypeExpression<NumberType>,
    prefix: TypeExpression<StringType>? = null,
) : FunctionExpression<StringType>("MB_LPAD", inStr, size, prefix)

fun mbLpad(
    inStr: TypeExpression<StringType>,
    size: TypeExpression<NumberType>,
    prefix: TypeExpression<StringType>? = null,
) = MBLpadExpression(inStr, size, prefix)

fun mbLpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, prefix: String) =
    mbLpad(inStr, size, prefix.toDopeType())

fun mbLpad(inStr: TypeExpression<StringType>, size: Number, prefix: TypeExpression<StringType>? = null) =
    mbLpad(inStr, size.toDopeType(), prefix)

fun mbLpad(inStr: String, size: TypeExpression<NumberType>, prefix: TypeExpression<StringType>? = null) =
    mbLpad(inStr.toDopeType(), size, prefix)

fun mbLpad(inStr: TypeExpression<StringType>, size: Number, prefix: String) =
    mbLpad(inStr, size.toDopeType(), prefix.toDopeType())

fun mbLpad(inStr: String, size: TypeExpression<NumberType>, prefix: String) =
    mbLpad(inStr.toDopeType(), size, prefix.toDopeType())

fun mbLpad(inStr: String, size: Number, prefix: TypeExpression<StringType>? = null) =
    mbLpad(inStr.toDopeType(), size.toDopeType(), prefix)

fun mbLpad(inStr: String, size: Number, prefix: String) =
    mbLpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())
