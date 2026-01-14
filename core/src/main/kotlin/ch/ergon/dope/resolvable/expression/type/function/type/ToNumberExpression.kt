package ch.ergon.dope.resolvable.expression.type.function.type

import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class ToNumberExpression<T : ValidType>(
    val expression: TypeExpression<T>,
    val filterChars: TypeExpression<StringType>? = null,
) : FunctionOperator<NumberType>

fun <T : ValidType> TypeExpression<T>.toNumber() = ToNumberExpression(this)

fun String.toNumber() = toDopeType().toNumber()

fun Boolean.toNumber() = toDopeType().toNumber()

fun TypeExpression<StringType>.toNumber(filterChars: TypeExpression<StringType>) =
    ToNumberExpression(this, filterChars)

fun TypeExpression<StringType>.toNumber(filterChars: String) = toNumber(filterChars.toDopeType())

fun String.toNumber(filterChars: TypeExpression<StringType>) = toDopeType().toNumber(filterChars)

fun String.toNumber(filterChars: String) = toDopeType().toNumber(filterChars.toDopeType())
