package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class LpadExpression(
    val inStr: TypeExpression<StringType>,
    val size: TypeExpression<NumberType>,
    val prefix: TypeExpression<StringType>? = null,
) : FunctionExpression<StringType>(listOf(inStr, size, prefix))

fun TypeExpression<StringType>.lpad(
    size: TypeExpression<NumberType>,
    prefix: TypeExpression<StringType>? = null,
) = LpadExpression(this, size, prefix)

fun TypeExpression<StringType>.lpad(size: TypeExpression<NumberType>, prefix: String) =
    lpad(size, prefix.toDopeType())

fun TypeExpression<StringType>.lpad(size: Number, prefix: TypeExpression<StringType>? = null) =
    lpad(size.toDopeType(), prefix)

fun TypeExpression<StringType>.lpad(size: Number, prefix: String) =
    lpad(size.toDopeType(), prefix.toDopeType())

fun String.lpad(
    size: TypeExpression<NumberType>,
    prefix: TypeExpression<StringType>? = null,
) = toDopeType().lpad(size, prefix)

fun String.lpad(size: TypeExpression<NumberType>, prefix: String) =
    toDopeType().lpad(size, prefix.toDopeType())

fun String.lpad(size: Number, prefix: TypeExpression<StringType>? = null) =
    toDopeType().lpad(size.toDopeType(), prefix)

fun String.lpad(size: Number, prefix: String) =
    toDopeType().lpad(size.toDopeType(), prefix.toDopeType())
