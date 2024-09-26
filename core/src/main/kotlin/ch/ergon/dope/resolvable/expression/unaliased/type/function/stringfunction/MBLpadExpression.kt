package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
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
    MBLpadExpression(inStr, size, prefix.toDopeType())

fun mbLpad(inStr: TypeExpression<StringType>, size: Number, prefix: TypeExpression<StringType>? = null) =
    MBLpadExpression(inStr, size.toDopeType(), prefix)

fun mbLpad(inStr: String, size: TypeExpression<NumberType>, prefix: TypeExpression<StringType>? = null) =
    MBLpadExpression(inStr.toDopeType(), size, prefix)

fun mbLpad(inStr: TypeExpression<StringType>, size: Number, prefix: String) =
    MBLpadExpression(inStr, size.toDopeType(), prefix.toDopeType())

fun mbLpad(inStr: String, size: TypeExpression<NumberType>, prefix: String) =
    MBLpadExpression(inStr.toDopeType(), size, prefix.toDopeType())

fun mbLpad(inStr: String, size: Number, prefix: TypeExpression<StringType>? = null) =
    MBLpadExpression(inStr.toDopeType(), size.toDopeType(), prefix)

fun mbLpad(inStr: String, size: Number, prefix: String) =
    MBLpadExpression(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())
