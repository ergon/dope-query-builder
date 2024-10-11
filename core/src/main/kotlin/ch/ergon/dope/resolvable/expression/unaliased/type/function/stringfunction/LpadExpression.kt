package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class LpadExpression(
    inStr: TypeExpression<StringType>,
    size: TypeExpression<NumberType>,
    prefix: TypeExpression<StringType>? = null,
) : FunctionExpression<StringType>("LPAD", inStr, size, prefix)

fun lpad(
    inStr: TypeExpression<StringType>,
    size: TypeExpression<NumberType>,
    prefix: TypeExpression<StringType>? = null,
) = LpadExpression(inStr, size, prefix)

fun lpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, prefix: String) =
    lpad(inStr, size, prefix.toDopeType())

fun lpad(inStr: TypeExpression<StringType>, size: Number, prefix: TypeExpression<StringType>? = null) =
    lpad(inStr, size.toDopeType(), prefix)

fun lpad(inStr: String, size: TypeExpression<NumberType>, prefix: TypeExpression<StringType>? = null) =
    lpad(inStr.toDopeType(), size, prefix)

fun lpad(inStr: TypeExpression<StringType>, size: Number, prefix: String) =
    lpad(inStr, size.toDopeType(), prefix.toDopeType())

fun lpad(inStr: String, size: TypeExpression<NumberType>, prefix: String) =
    lpad(inStr.toDopeType(), size, prefix.toDopeType())

fun lpad(inStr: String, size: Number, prefix: TypeExpression<StringType>? = null) =
    lpad(inStr.toDopeType(), size.toDopeType(), prefix)

fun lpad(inStr: String, size: Number, prefix: String) =
    lpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())
