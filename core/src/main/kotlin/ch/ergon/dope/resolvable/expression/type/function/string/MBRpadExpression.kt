package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class MBRpadExpression(
    val inStr: TypeExpression<StringType>,
    val size: TypeExpression<NumberType>,
    val postfix: TypeExpression<StringType>? = null,
) : FunctionExpression<StringType>(listOf(inStr, size, postfix))

fun TypeExpression<StringType>.mbRpad(
    size: TypeExpression<NumberType>,
    postfix: TypeExpression<StringType>? = null,
) = MBRpadExpression(this, size, postfix)

fun TypeExpression<StringType>.mbRpad(size: TypeExpression<NumberType>, postfix: String) =
    mbRpad(size, postfix.toDopeType())

fun TypeExpression<StringType>.mbRpad(size: Number, postfix: TypeExpression<StringType>? = null) =
    mbRpad(size.toDopeType(), postfix)

fun TypeExpression<StringType>.mbRpad(size: Number, postfix: String) =
    mbRpad(size.toDopeType(), postfix.toDopeType())

fun String.mbRpad(
    size: TypeExpression<NumberType>,
    postfix: TypeExpression<StringType>? = null,
) = toDopeType().mbRpad(size, postfix)

fun String.mbRpad(size: TypeExpression<NumberType>, postfix: String) =
    toDopeType().mbRpad(size, postfix.toDopeType())

fun String.mbRpad(size: Number, postfix: TypeExpression<StringType>? = null) =
    toDopeType().mbRpad(size.toDopeType(), postfix)

fun String.mbRpad(size: Number, postfix: String) =
    toDopeType().mbRpad(size.toDopeType(), postfix.toDopeType())
