package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class MBLpadExpression(
    val inStr: TypeExpression<StringType>,
    val size: TypeExpression<NumberType>,
    val prefix: TypeExpression<StringType>? = null,
) : FunctionExpression<StringType>(listOf(inStr, size, prefix))

fun TypeExpression<StringType>.mbLpad(
    size: TypeExpression<NumberType>,
    prefix: TypeExpression<StringType>? = null,
) = MBLpadExpression(this, size, prefix)

fun TypeExpression<StringType>.mbLpad(size: TypeExpression<NumberType>, prefix: String) =
    mbLpad(size, prefix.toDopeType())

fun TypeExpression<StringType>.mbLpad(size: Number, prefix: TypeExpression<StringType>? = null) =
    mbLpad(size.toDopeType(), prefix)

fun TypeExpression<StringType>.mbLpad(size: Number, prefix: String) =
    mbLpad(size.toDopeType(), prefix.toDopeType())

fun String.mbLpad(
    size: TypeExpression<NumberType>,
    prefix: TypeExpression<StringType>? = null,
) = toDopeType().mbLpad(size, prefix)

fun String.mbLpad(size: TypeExpression<NumberType>, prefix: String) =
    toDopeType().mbLpad(size, prefix.toDopeType())

fun String.mbLpad(size: Number, prefix: TypeExpression<StringType>? = null) =
    toDopeType().mbLpad(size.toDopeType(), prefix)

fun String.mbLpad(size: Number, prefix: String) =
    toDopeType().mbLpad(size.toDopeType(), prefix.toDopeType())
